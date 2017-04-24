/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.QuestionType;

import java.util.ArrayList;

/**
 * The Interface QuestionTypeDao.
 */
public interface QuestionTypeDao {
	

	/**
	 * Gets the question type list.
	 *
	 * @return the question type list
	 */
	public ArrayList<QuestionType> getQuestionTypeList();
	

	/**
	 * Gets the type of question.
	 *
	 * @param questionID the question ID
	 * @return the type of question
	 */
	public  int getTypeOfQuestion(long questionID);
}
