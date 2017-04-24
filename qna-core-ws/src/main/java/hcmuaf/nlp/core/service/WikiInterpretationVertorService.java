/**
 * @author Manh Phan
 *
 * Edited date Mar 4, 2017
 */
package hcmuaf.nlp.core.service;

import hcmuaf.nlp.core.model.QuestionVector;

import java.util.List;
import java.util.Map;

/**
 * The Interface WikiInterpretationVertorService.
 */
public interface WikiInterpretationVertorService {

	/**
	 * Builds the interpretation vector.
	 *
	 * @param questionID
	 *            the question id
	 * @return the hash map
	 */
	public abstract Map<Integer, Double> buildInterpretationVector(long questionID);

	/**
	 * Build interpretation vector.
	 *
	 * @param listQuestionVector the list question vector
	 * @return the map
	 */
	public abstract Map<Integer, Double> buildInterpretationVector(List<QuestionVector> listQuestionVector);

}