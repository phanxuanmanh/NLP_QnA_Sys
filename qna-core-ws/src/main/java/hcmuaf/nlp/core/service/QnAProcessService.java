/**
 * @author Manh Phan
 *
 * Edited date Jun 24, 2016
 */
package hcmuaf.nlp.core.service;

import hcmuaf.nlp.core.dto.QnAPair;
import hcmuaf.nlp.core.model.Answer;
import hcmuaf.nlp.core.model.Question;

import java.util.List;

/**
 * The Interface QnAProcessService.
 */
public interface QnAProcessService {

	/**
	 * Gets the list relate QnA.
	 *
	 * @param question
	 *            the question
	 * @return the list relate QnA
	 */
	public List<QnAPair> getListRelateQnA(String question);
	
	/**
	 * Update QnA pair.
	 *
	 * @param qnaPair the QnA pair
	 */
	public void addAnswer(long questionId, String answer);
	
	
	/**
	 * Gets the recent qnA.
	 *
	 * @param questionId the question id
	 * @return the list recent QnAs
	 */
	public List<QnAPair> getQnAByQuestionId(Long questionId);
	
	/**
	 * Gets the answer by question id.
	 *
	 * @param questionId the question id
	 * @return the list answer for the question
	 */
	public List<Answer> getAnswerByQuestionId(Long questionId);

	/**
	 * Gets the question by id.
	 *
	 * @param questionId the question id
	 * @return the question
	 */
	public Question getQuestionById(Long questionId);
}
