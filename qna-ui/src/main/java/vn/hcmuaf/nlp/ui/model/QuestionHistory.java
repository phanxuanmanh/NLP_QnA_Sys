/**
 * @author Manh Phan
 *
 * Edited date Jul 30, 2016
 */
package vn.hcmuaf.nlp.ui.model;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class QuestionHistory.
 */

public class QuestionHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Integer id;

	/** The question id. */
	private Integer questionId;

	/** The reference question id. */
	private Integer referenceQuestionId;

	/** The rating. */
	private Double rating;

	/** The created date. */
	private Date createdDate;

	/** The create user id. */
	private Integer createUserId;

	/** The answer user id. */
	private Integer answerUserId;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the question id.
	 *
	 * @return the question id
	 */
	public Integer getQuestionId() {
		return questionId;
	}

	/**
	 * Sets the question id.
	 *
	 * @param questionId
	 *            the new question id
	 */
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	/**
	 * Gets the reference question id.
	 *
	 * @return the reference question id
	 */
	public Integer getReferenceQuestionId() {
		return referenceQuestionId;
	}

	/**
	 * Sets the reference question id.
	 *
	 * @param referenceQuestionId
	 *            the new reference question id
	 */
	public void setReferenceQuestionId(Integer referenceQuestionId) {
		this.referenceQuestionId = referenceQuestionId;
	}

	/**
	 * Gets the rating.
	 *
	 * @return the rating
	 */
	public Double getRating() {
		return rating;
	}

	/**
	 * Sets the rating.
	 *
	 * @param rating
	 *            the new rating
	 */
	public void setRating(Double rating) {
		this.rating = rating;
	}

	/**
	 * Gets the created date.
	 *
	 * @return the created date
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * Sets the created date.
	 *
	 * @param createdDate
	 *            the new created date
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Gets the creates the user id.
	 *
	 * @return the creates the user id
	 */
	public Integer getCreateUserId() {
		return createUserId;
	}

	/**
	 * Sets the creates the user id.
	 *
	 * @param createUserId
	 *            the new creates the user id
	 */
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * Gets the answer user id.
	 *
	 * @return the answer user id
	 */
	public Integer getAnswerUserId() {
		return answerUserId;
	}

	/**
	 * Sets the answer user id.
	 *
	 * @param answerUserId
	 *            the new answer user id
	 */
	public void setAnswerUserId(Integer answerUserId) {
		this.answerUserId = answerUserId;
	}

}
