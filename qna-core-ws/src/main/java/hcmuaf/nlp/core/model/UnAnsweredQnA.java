package hcmuaf.nlp.core.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
public class UnAnsweredQnA {
	@Id
	private Long id;
	private Question question;
	private String answer;
	private String createUserName;
	private String answerUserName;
	private Date createdDate;
	private Date expiredDate;
	private Integer createUserId;
	private Integer answerUserId;
	private Long referenceQuestionId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getAnswerUserName() {
		return answerUserName;
	}
	public void setAnswerUserName(String answerUserName) {
		this.answerUserName = answerUserName;
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
	public Long getReferenceQuestionId() {
		return referenceQuestionId;
	}
	public void setReferenceQuestionId(Long referenceQuestionId) {
		this.referenceQuestionId = referenceQuestionId;
	}
	public Date getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	
	
	
}
