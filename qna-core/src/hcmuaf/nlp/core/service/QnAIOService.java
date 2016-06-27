/**
 * @author Manh Phan
 *
 * Edited date Jun 24, 2016
 */
package hcmuaf.nlp.core.service;

/**
 * The Interface QnAIOService.
 */
public interface QnAIOService {
	
	/**
	 * Insert question.
	 *
	 * @param question the question
	 * @return the integer
	 */
	public Integer insertQuestion(String question);

	/**
	 * Adds the question type.
	 *
	 * @param typeId the type id
	 */
	public void addQuestionType(Integer typeId);

	/**
	 * Adds the answer.
	 *
	 * @param answer the answer
	 * @param questionId the question id
	 * @return the integer
	 */
	public Integer addAnswer(String answer, Integer questionId);

}
