/**
 * @author Manh Phan
 *
 * Edited date Aug 15, 2016
 */
package vn.hcmuaf.nlp.manage.service;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import vn.hcmuaf.nlp.ui.model.Answer;
import vn.hcmuaf.nlp.ui.model.Question;
import vn.hcmuaf.nlp.ui.model.QuestionHistory;
import vn.hcmuaf.nlp.ui.model.UnAnsweredQnA;

/**
 * The Interface QuestionHistoryService.
 */
public interface QuestionHistoryService {

	/**
	 * Gets the list recent question history.
	 *
	 * @param from
	 *            the from
	 * @param size
	 *            the size
	 * @param firstResult
	 *            the first result
	 * @return the list recent question history
	 * @throws URISyntaxException
	 */
	public List<QuestionHistory> getListRecentQuestionHistory(Date from, int size, int firstResult)
			throws URISyntaxException;

	/**
	 * Gets the list un-answer question.
	 *
	 * @param from
	 *            the from
	 * @param size
	 *            the size
	 * @param firstResult
	 *            the first result
	 * @return the list un-answer question
	 * @throws URISyntaxException
	 */
	public List<UnAnsweredQnA> getListUnAnswerQuestion(Date from)
			throws URISyntaxException;

	/**
	 * Gets the qna list by question id.
	 *
	 * @param QuestionId
	 *            the question id
	 * @return the qna list by question id
	 * @throws URISyntaxException
	 *             the URI syntax exception
	 */
	public Answer getAnswerByQuestionId(Integer QuestionId) throws URISyntaxException;

	/**
	 * Gets the content by question id.
	 *
	 * @param QuestionId the question id
	 * @return the content by question id
	 * @throws URISyntaxException the URI syntax exception
	 */
	public Question getContentByQuestionId(int QuestionId) throws URISyntaxException;
	
	/**
	 * Delete question history.
	 *
	 * @param historyId the history id
	 * @return the question
	 * @throws URISyntaxException the URI syntax exception
	 */
	public void deleteQuestionHistory(int historyId) throws URISyntaxException;
	
	public void answerQuestion(UnAnsweredQnA qna) throws URISyntaxException;
}
