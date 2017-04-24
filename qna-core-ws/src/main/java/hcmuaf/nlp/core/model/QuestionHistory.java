/**
 * @author Manh Phan
 *
 * Edited date Jul 30, 2016
 */
package hcmuaf.nlp.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class QuestionHistory.
 */
@Entity
@Table(name = "QUESTION_HISTORY")
public class QuestionHistory {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	/** The question id. */
	@Column(name = "q_id")
	private Long questionId;
	
	/** The reference question id. */
	@Column(name = "ref_q_id")
	private Long referenceQuestionId;
	
	/** The rating. */
	@Column(name = "rating")
	private Double rating;
	
	/** The created date. */
	@Column(name = "created_date")
	private Date createdDate;
	
	/** The create user id. */
	@Column(name = "created_user")
	private Integer createUserId;
	
	/** The answer user id. */
	@Column(name = "answer_user")
	private Integer answerUserId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Long getReferenceQuestionId() {
		return referenceQuestionId;
	}

	public void setReferenceQuestionId(Long referenceQuestionId) {
		this.referenceQuestionId = referenceQuestionId;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getAnswerUserId() {
		return answerUserId;
	}

	public void setAnswerUserId(Integer answerUserId) {
		this.answerUserId = answerUserId;
	}


}
