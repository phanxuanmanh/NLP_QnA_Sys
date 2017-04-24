/**
 * @author Manh Phan
 *
 * Edited date Jul 30, 2016
 */
package hcmuaf.nlp.core.dao;

import java.util.Date;
import java.util.List;

import hcmuaf.nlp.core.model.Question;
import hcmuaf.nlp.core.model.QuestionHistory;

/**
 * The Interface QuestionHistoryDao.
 */
public interface QuestionHistoryDao {

	/**
	 * Gets the question history by id.
	 *
	 * @param historyId
	 *            the history id
	 * @return the question history by id
	 */
	public QuestionHistory getQuestionHistoryById(Long historyId);

	/**
	 * Gets the question history root question.
	 *
	 * @param questionId
	 *            the question id
	 * @return the question history root question
	 */
	public QuestionHistory getQuestionHistoryRootQuestion(Long questionId);

	/**
	 * Gets the question history root refer question.
	 *
	 * @param questionId
	 *            the question id
	 * @return the question history root refer question
	 */
	public List<QuestionHistory> getQuestionHistoryRootReferQuestion(Long questionId);

	/**
	 * Insert question history root question.
	 *
	 * @param questionHistory
	 *            the question history
	 */
	public void insertQuestionHistory(QuestionHistory questionHistory);
	
	/**
	 * Delete question history.
	 *
	 * @param historyId the history id
	 */
	public void deleteQuestionHistory(Long historyId);

	/**
	 * Update question history.
	 *
	 * @param questionHistory the question history
	 */
	public void updateQuestionHistory(QuestionHistory questionHistory);

	/**
	 * Gets the list common question.
	 *
	 * @param from
	 *            the from
	 * @param fetchSize
	 *            the fetch size
	 * @return the list common question
	 */
	public List<Question> getRecentQuestions(Date from);

	/**
	 * Gets the list recent un-answered questions.
	 *
	 * @param startQuestionDate the min date that questions were posted.
	 * @return the list recent un-answered questions.
	 */
	public List<QuestionHistory> getListRecentUnAnswer(Date startQuestionDate);


	/**
	 * Gets the list recent QnA.
	 *
	 * @param from the min date that questions were posted.
	 * @param fetchSize the fetch size
	 * @param firstResult the first result
	 * @return the list recent QnA
	 */
	public List<QuestionHistory> getListRecentQnA(Date from, int fetchSize, int firstResult);
	
	/**
	 * Gets the history by user id.
	 *
	 * @param userId the user id
	 * @return the histories by user id
	 */
	public List<QuestionHistory> getHistoryByUserId(int userId);
}
