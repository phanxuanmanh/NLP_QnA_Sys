/**
 * @author Manh Phan
 *
 * Edited date Mar 4, 2017
 */
package hcmuaf.nlp.core.service;

import hcmuaf.nlp.core.model.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Interface WikiConceptVectorAccessor.
 */
public interface WikiConceptVectorAccessor {
	/**
	 * Gets the number of concept.
	 *
	 * @return the number of concept
	 */
	public abstract long getNumberOfConcept();
	
	/**
	 * Gets the list concept id.
	 *
	 * @return the list concept id
	 */
	public abstract List<Integer> getListConceptId();
	
	/**
	 * Gets the list word frequency.
	 *
	 * @return the list word frequency
	 */
	public abstract HashMap<Long, Integer> getListWordFreq();
	
	/**
	 * Number of concept contain word.
	 *
	 * @param wordID
	 *            the word id
	 * @return the number of concept contain word
	 */
	public abstract int numberOfConceptContainWord(int wordID);
	
	/**
	 * Gets the list wiki concept by word.
	 *
	 * @return the list wiki concept by word
	 */
	public abstract Map<Long, List<Tuple<Integer, Double>>> getInvertedIndexMap();
	
	/**
	 * Gets the list concept by word id.
	 *
	 * @param wordID
	 *            the word id
	 * @return the list concept by word id
	 */
	public abstract List<Tuple<Integer, Double>> getListConceptByWordId(long wordID);
}