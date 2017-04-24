/**
 * @author Manh Phan
 *
 * Edited date Mar 4, 2017
 */
package hcmuaf.nlp.core.service;

import java.util.HashMap;
import java.util.Map;

/**
 * The Interface QuestionComparator.
 */
public interface QuestionComparator {

	/**
	 * Compare vector.
	 *
	 * @param vector1 the vector 1
	 * @param vector2 the vector 2
	 * @return the relation rate
	 */
	public abstract double compareVector(Map<Integer, Double> vector1, Map<Integer, Double> vector2);

	/**
	 * Gets the relation list.
	 *
	 * @param questionId the question id
	 * @return the relation question list
	 */
	public abstract HashMap<Long, Double> getRelationList(long questionId);

}