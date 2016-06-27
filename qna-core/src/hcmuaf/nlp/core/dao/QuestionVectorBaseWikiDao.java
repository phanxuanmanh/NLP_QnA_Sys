/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.QuestionVectorBaseWiki;

import java.util.List;

/**
 * The Interface QuestionVectorBaseWikiDao.
 */
public interface QuestionVectorBaseWikiDao {
	
	/**
	 * Gets the list vector.
	 *
	 * @return the list vector
	 */
	public List<QuestionVectorBaseWiki> getListVector();

	/**
	 * Update question vector.
	 *
	 * @param vector the vector
	 */
	public void updateQuestionVector(QuestionVectorBaseWiki vector);

	/**
	 * Update list question vector.
	 *
	 * @param listVector the list vector
	 */
	public void updateListQuestionVector(List<QuestionVectorBaseWiki> listVector);

	/**
	 * Gets the list vector.
	 *
	 * @param questionId the question id
	 * @return the list vector
	 */
	public List<QuestionVectorBaseWiki> getListVector(int questionId);

	/**
	 * Gets the list question id.
	 *
	 * @return the list question id
	 */
	public List<Integer> getListQuestionId();

	/**
	 * Gets the list word id.
	 *
	 * @return the list word id
	 */
	public List<Integer> getListWordId();
}
