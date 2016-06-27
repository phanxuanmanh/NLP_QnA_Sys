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
	 * Gets the type list.
	 *
	 * @return the type list
	 */
	public ArrayList<QuestionType> getTypeList();
	
	/**
	 * Gets the question type.
	 *
	 * @param qId the q id
	 * @return the question type
	 */
	public  int getQuestionType(int qId);
}
