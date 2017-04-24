/**
 * @author Manh Phan
 *
 * Edited date Jul 16, 2016
 */
package vn.hcmuaf.nlp.ui.service;

import java.net.URISyntaxException;
import java.util.List;

import vn.hcmuaf.nlp.ui.model.CommonQuestionSearchCriteria;
import vn.hcmuaf.nlp.ui.model.QnAPair;
import vn.hcmuaf.nlp.ui.model.QuestionHistory;

/**
 * The Interface QnAService.
 */
public interface QnAService {

	/**
	 * Gets the relate qn A.
	 *
	 * @param question
	 *            the question
	 * @return the relate qn A
	 * @throws URISyntaxException
	 */
	public List<QnAPair> getRelateQnA(String question) throws URISyntaxException;

	/**
	 * Gets the qn A.
	 *
	 * @param questionId
	 *            the question id
	 * @return the qn A
	 */
	public List<QnAPair> getQnA(int questionId);

	/**
	 * Update qn A.
	 *
	 * @param qnaPair
	 *            the qna pair
	 * @return the integer
	 */
	public Integer updateQnA(QnAPair qnaPair);

	/**
	 * Inser question history.
	 *
	 * @param history the history
	 * @throws URISyntaxException 
	 */
	public void inserQuestionHistory(QuestionHistory history) throws URISyntaxException;
	
	public List<QnAPair> getCommonQnA(CommonQuestionSearchCriteria searchCriteria) throws Exception;
	
	public List<QnAPair> getQnAHistoryByUser(int userId) throws Exception;
}
