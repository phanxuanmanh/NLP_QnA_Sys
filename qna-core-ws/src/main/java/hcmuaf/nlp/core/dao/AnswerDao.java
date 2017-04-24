/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.Answer;

/**
 * The Interface AnswerDao.
 */
public interface AnswerDao {
	
	/**
	 * Insert answer.
	 *
	 * @param answer The answer.
	 * @return the integer
	 */
	public Long insertAnswer(String answer);
	
	/**
	 * Get the answer by ID.
	 *
	 * @param answerId The answer id.
	 * @return the answer
	 */
	public Answer getAnswer(Long answerId);
}
