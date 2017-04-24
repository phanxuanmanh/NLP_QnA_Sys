/**
 * @author Manh Phan
 *
 * Edited date Aug 15, 2016
 */
package hcmuaf.nlp.core.service;

import hcmuaf.nlp.core.model.QuestionHistory;
import hcmuaf.nlp.core.model.UnAnsweredQnA;

import java.util.Date;
import java.util.List;

/**
 * The Interface QnAManageService.
 */
public interface QnAManageService {

	/**
	 * Adds the answer.
	 *
	 * @param questionId
	 *            the question id
	 * @param answer
	 *            the answer
	 * @return the integer
	 */
	public Long addAnswer(UnAnsweredQnA qna);


	/**
	 * Gets the recent un-answer question.
	 *
	 * @param from
	 *            the from
	 * @param fetchSize
	 *            the fetch size
	 * @param firstResult
	 *            the first result
	 * @return the recent un-answer question
	 */
	public List<UnAnsweredQnA> getRecentUnAnswerQuestion(Date from);

	/**
	 * Gets the recent qna.
	 *
	 * @param from
	 *            the from
	 * @param fetchSize
	 *            the fetch size
	 * @param firstResult
	 *            the first result
	 * @return the recent qna
	 */
	public List<QuestionHistory> getRecentQnA(Date from, int fetchSize, int firstResult);

}
