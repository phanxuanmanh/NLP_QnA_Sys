/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.service.impl;

import hcmuaf.nlp.core.dao.WikiConceptWordDao;
import hcmuaf.nlp.core.model.Tuple;
import hcmuaf.nlp.core.model.WikiConceptWord;
import hcmuaf.nlp.core.service.WikiConceptVectorAccessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class WikiConceptVectorAccessor.
 */
@Service
public class WikiConceptVectorAccessorImpl implements WikiConceptVectorAccessor {
	/** The number of concept. */
	private static long numberOfConcept = 0;
	/** The inverted index vector dao. */
	@Autowired
	private WikiConceptWordDao wikiConcepWordDao;
	/** The list word freq. */
	private static HashMap<Long, Integer> listWordFreq;
	/** The list page id. */
	private static List<Integer> listPageId;
	/** The list wiki con cept by word. */
	private static Map<Long, List<Tuple<Integer, Double>>> invertedIndexMap;
	
	/**
	 * Gets the number of concept.
	 *
	 * @return the number of concept
	 */
	@Override
	public long getNumberOfConcept() {
		if (numberOfConcept == 0) {
			numberOfConcept = wikiConcepWordDao.currentNumberOfConcept();
			System.out.println("Finish load number of concept!!");
		}
		return numberOfConcept;
	}
	
	/**
	 * Gets the list concept id.
	 *
	 * @return the list concept id
	 */
	@Override
	public List<Integer> getListConceptId() {
		if (listPageId == null) {
			listPageId = wikiConcepWordDao.getListPageId();
			System.out.println("Finish list concept Id!!");
		}
		return listPageId;
	}
	
	/**
	 * Gets the list word freq.
	 *
	 * @return the list word freq
	 */
	@Override
	public HashMap<Long, Integer> getListWordFreq() {
		if (listWordFreq == null) {
			listWordFreq = wikiConcepWordDao.getWordWithFreq();
			System.out.println("Finish load list of words with frequence!!");
		}
		return listWordFreq;
	}
	
	/**
	 * Number of concept contain word.
	 *
	 * @param wordID
	 *            the word id
	 * @return the int
	 */
	@Override
	public int numberOfConceptContainWord(int wordID) {
		Integer numberOfConcept = getListWordFreq().get(Integer.valueOf(wordID));
		if (numberOfConcept == null) {
			return 0;
		} else {
			return numberOfConcept.intValue();
		}
	}
	
	/**
	 * Gets the list wiki con cept by word.
	 *
	 * @return the list wiki con cept by word
	 */
	@Override
	public Map<Long, List<Tuple<Integer, Double>>> getInvertedIndexMap() {
		if (invertedIndexMap == null) {
			List<WikiConceptWord> wikiConceptWords = wikiConcepWordDao.getAllVector();
			invertedIndexMap = 
					wikiConceptWords.parallelStream()
					.collect(
					Collectors.groupingByConcurrent(WikiConceptWord::getWordId,
							Collectors.mapping(
									wikiConcepWord 
									-> new Tuple<Integer, Double>(wikiConcepWord.getPageId()
									, wikiConcepWord.getTfidf()),
									Collectors.toList())));
		}
		return invertedIndexMap;
	}
	
	/**
	 * Gets the list concept by word id.
	 *
	 * @param wordID
	 *            the word id
	 * @return the list concept by word id
	 */
	@Override
	public List<Tuple<Integer, Double>> getListConceptByWordId(long wordID) {
		return getInvertedIndexMap().get(wordID);
	}
}
