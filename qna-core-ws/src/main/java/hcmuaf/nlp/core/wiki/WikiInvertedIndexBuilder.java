/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.wiki;

import hcmuaf.nlp.core.dao.WikiConceptWordDao;
import hcmuaf.nlp.core.hibernateDao.impl.WikiConceptWordDaoImpl;
import hcmuaf.nlp.core.model.WikiConceptWord;

import java.util.HashMap;
import java.util.List;

/**
 * The Class WikiInvertedIndexBuilder.
 */
public class WikiInvertedIndexBuilder {
	
	/** The list word freq. */
	private static HashMap<Long, Integer> listWordFreq;
	
	/** The number of concept. */
	private static long numberOfConcept;
	
	/** The vector dao. */
	private WikiConceptWordDao vectorDao;

	/**
	 * Instantiates a new wiki inverted index builder.
	 */
	public WikiInvertedIndexBuilder() {
		vectorDao = new WikiConceptWordDaoImpl();
		listWordFreq = vectorDao.getWordWithFreq();
		numberOfConcept = vectorDao.currentNumberOfConcept();
	}

	/**
	 * Gets the list page id.
	 *
	 * @return the list page id
	 */
	public List<Integer> getListPageId() {
		return vectorDao.getListPageId();
	}

	/**
	 * Concept process.
	 *
	 * @param pageId the page id
	 */
	public void conceptProcess(int pageId) {
		long init = System.currentTimeMillis();
		List<WikiConceptWord> listWordVectors = vectorDao
				.listWordVectorByPage(pageId);
		long numberOfWord =0L;
		for(WikiConceptWord wcw : listWordVectors){
			numberOfWord+=wcw.getFreq();
		}
		for (WikiConceptWord vector : listWordVectors) {
			long wordId = vector.getWordId();
			int freq = vector.getFreq();
			Integer numberOfConceptContainWord = listWordFreq.get(Long
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
