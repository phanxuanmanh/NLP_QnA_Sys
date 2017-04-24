/**
 * @author Manh Phan
 *
 * Edited date Mar 4, 2017
 */
package hcmuaf.nlp.core.service;

import hcmuaf.nlp.core.dto.QnAPair;
import hcmuaf.nlp.core.model.QuestionHistory;

import java.util.Date;
import java.util.List;

/**
 * The Interface QnAHistoryService.
 */
public interface QnAHistoryService {
	
	/**
	 * Gets the question history.
	 *
	 * @param startDate the start question date 
	 * @param typeName the type name
	 * @param keyWord the key word
	 * @param size the fetch size
	 * @return the list question history
	 */
	public List<QnAPair> getQuestionHistory(Date startDate,String typeName,String keyWord, long size);
	
	/**
	 * Add the question history.
	 *
	 * @param history the history
	 */
	public void addQuestionHistory(QuestionHistory history);

	/**
	 * Delete history.
	 *
	 * @param historyId
	 *            the history id
	 * @return the integer
	 */
	public void deleteHistory(Long historyId);;

	/**
	 * Update history.
	 *
	 * @param history
	 *            the history
	 */
	public void updateHistory(QuestionHistory history);
	
	/**
	 * Gets the user QnA history.
	 *
	 * @param userId the user id
	 * @return the user QnA history
	 */
	public List<QnAPair> getUserQnAHistory(int userId);
}
