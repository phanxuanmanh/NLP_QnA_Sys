/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.QuestionVectorCsv;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The Interface QuestionDao.
 */
public interface QuestionDao {
	
	/**
	 * Gets the question list.
	 *
	 * @return the question list
	 */
	public ArrayList<Integer> getQuestionList();
	
	/**
	 * Count question.
	 *
	 * @return the int
	 */
	public  int countQuestion();
	
	/**
	 * Gets the question type.
	 *
	 * @param qId the q id
	 * @return the question type
	 */
	public int getQuestionType(int qId);

	/**
	 * Gets the question.
	 *
	 * @param qId the q id
	 * @return the question
	 */
	public String getQuestion(int qId);

	/**
	 * Insert question.
	 *
	 * @param question the question
	 * @param typeID the type id
	 * @return the int
	 */
	public int insertQuestion(String question,int typeID);

	/**
	 * Read question vector data.
	 *
	 * @param questionID the question id
	 * @return the question vector csv
	 * @throws SQLException the SQL exception
	 */
	public QuestionVectorCsv readQuestionVectorData(int questionID) throws SQLException;

	/**
	 * Read question vector data.
	 *
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<QuestionVectorCsv> readQuestionVectorData() throws SQLException;

}
