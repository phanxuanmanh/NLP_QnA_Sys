/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.dto;

import java.io.Serializable;

/**
 * The Class QnAPair.
 */
public class QnAPair implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The question id. */
	private Long questionId;
	/** The question. */
	private String question;
	
	/** The answer id. */
	private Long answerId;
	/** The answer. */
	private String answer;
	
	/** The type id. */
	private Integer typeID;
	
	/**
	 * Instantiates a new qn a pair.
	 */
	public QnAPair() {
	}

	/**
	 * Instantiates a new qn a pair.
	 *
	 * @param question the question
	 * @param answer the answer
	 * @param typeID the type id
	 */
	public QnAPair(String question, String answer, int typeID) {
		super();
		this.question = question;
		this.answer = answer;
		this.typeID = typeID;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	/**
	 * Gets the question.
	 *
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * Sets the question.
	 *
	 * @param question the new question
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * Gets the answer.
	 *
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * Sets the answer.
	 *
	 * @param answer the new answer
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * Gets the type id.
	 *
	 * @return the type id
	 */
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Question: " + question + "-- Answer: " + answer
				+ " -- Type Id: " + typeID;
	}

	public Integer getTypeID() {
		return typeID;
	}

	public void setTypeID(Integer typeID) {
		this.typeID = typeID;
	}

}
