package hcmuaf.nlp.core.wiki;

import hcmuaf.nlp.core.dao.WikiConceptVectorDao;
import hcmuaf.nlp.core.hibernateDao.impl.WikiConceptVectorDaoImpl;
import hcmuaf.nlp.core.model.WikiConceptWord;

import java.util.HashMap;
import java.util.List;

public class WikiInvertedIndexBuilder {
	private static HashMap<Integer, Integer> listWordFreq;
	private static long numberOfConcept;
	private WikiConceptVectorDao vectorDao;

	public WikiInvertedIndexBuilder() {
		vectorDao = new WikiConceptVectorDaoImpl();
		listWordFreq = vectorDao.getWordWithFreq();
		numberOfConcept = vectorDao.currentNumberOfConcept();
	}

	public List<Integer> getListPageId() {
		return vectorDao.getListPageId();
	}

	public void conceptProcess(int pageId) {
		long init = System.currentTimeMillis();
		List<WikiConceptWord> listWordVectors = vectorDao
				.listWordVector(pageId);
		long numberOfWord =0L;
		for(WikiConceptWord wcw : listWordVectors){
			numberOfWord+=wcw.getFreq();
		}
		for (WikiConceptWord vector : listWordVectors) {
			int wordId = vector.getWordId();
			int freq = vector.getFreq();
			Integer numberOfConceptContainWord = listWordFreq.get(Integer
					.valueOf(wordId));
			double weight = ((double) freq / numberOfWord)
					* Math.log10((double) numberOfConcept
							/ numberOfConceptContainWord.intValue());
			vector.setTfidf(weight);
		}
		
		vectorDao.updateListConceptWord(listWordVectors);
		long end = System.currentTimeMillis();
		System.out.println("done update on page: " + pageId + " time:"
				+ (end -init));
		
	}

}
