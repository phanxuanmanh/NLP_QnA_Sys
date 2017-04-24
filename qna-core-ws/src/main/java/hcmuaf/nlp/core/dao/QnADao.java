/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.dao;

import java.util.List;

import hcmuaf.nlp.core.dto.QnAPair;
import hcmuaf.nlp.core.model.Answer;
import hcmuaf.nlp.core.model.QnAPairEntity;

/**
 * The Interface QnADao.
 */
public interface QnADao {

	/**
	 * Insert qn a pair.
	 *
	 * @param pair
	 *            the pair
	 */
	public void insertQnAPair(QnAPair pair);

	/**
	 * Insert qn a pair entity.
	 *
	 * @param pair
	 *            The pair entity.
	 */
	public void insertQnAPair(QnAPairEntity pair);

	/**
	 * Gets the QnA pairs.
	 *
	 * @param questionId the question id
	 * @return the QnA pairs.
	 */
	public List<QnAPairEntity> getQnAPairs(Long questionId);

	/**
	 * Gets the answers by question id.
	 *
	 * @param questionID the question ID
	 * @return the answers by question id
	 */
	public List<Answer> getAnswersByQuestionId(Long questionID);
}
