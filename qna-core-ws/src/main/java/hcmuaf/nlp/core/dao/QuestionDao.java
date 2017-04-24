/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.Question;
import hcmuaf.nlp.core.model.QuestionVectorCsv;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Interface QuestionDao.
 */
public interface QuestionDao {

	/**
	 * Gets the available question list.
	 *
	 * @return the available question list
	 */
	public List<Long> getAvailableQuestionList();
	
	/**
	 * Count question.
	 *
	 * @return the number of question
	 */
	public  int countQuestion();
	
	/**
	 * Gets the question type.
	 *
	 * @param questionId the question id
	 * @return the question type
	 */
	public int getQuestionType(long questionId);

	/**
	 * Gets the question content.
	 *
	 * @param questionID the question id
	 * @return the question
	 */
	public String getQuestionContent(long questionID);
	
	/**
	 * Gets the question by ID.
	 *
	 * @param questionId the question id
	 * @return the question
	 */
	public Question getQuestionByID(long questionId);

	/**
	 * Insert question.
	 *
	 * @param question the question
	 * @param typeID the type id
	 * @return the question id
	 */
	public Long insertQuestion(String question,Integer typeID);
	
	/**
	 * Sets the type for question.
	 *
	 * @param questionId the question id
	 * @param typeID the type ID
	 */
	public void setQuestionType(long questionId,Integer typeID);

	/**
	 * Read question vector data.
	 *
	 * @param questionID the question id
	 * @return the question vector csv
	 * @throws SQLException the SQL exception
	 */
	public QuestionVectorCsv readQuestionVectorData(long questionID) throws SQLException;

	/**
	 * Read question vector data.
	 *
	 * @return the vecto
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<QuestionVectorCsv> readQuestionVectorData() throws SQLException;

	/**
	 * Update question.
	 *
	 * @param question the question
	 */
	public void updateQuestion(Question question);

}
