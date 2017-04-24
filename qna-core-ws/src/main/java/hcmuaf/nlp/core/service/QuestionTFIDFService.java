/**
 * @author Manh Phan
 *
 * Edited date Mar 4, 2017
 */
package hcmuaf.nlp.core.service;

import hcmuaf.nlp.core.model.QuestionVector;

import java.util.List;

/**
 * The Interface QuestionTFIDFService.
 */
public interface QuestionTFIDFService {

	/**
	 * Calculate TFIDF.
	 */
	public abstract void calculateTFIDF();

	/**
	 * Calculate TFIDF.
	 *
	 * @param questionId the question id
	 * @return the list question vector
	 */
	public abstract List<QuestionVector> calculateTFIDF(long questionId);

}