package hcmuaf.nlp.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class QuestionWordPair implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "q_id")
	private int questionID;
	@Column(name = "wid")
	private int wordID;

	public QuestionWordPair(int questionID, int wordID) {
		super();
		this.questionID = questionID;
		this.wordID = wordID;
	}

	public QuestionWordPair() {
		super();
	}

	public int getQuestionID() {
		return questionID;
	}

	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	public int getWordID() {
		return wordID;
	}

	public void setWordID(int wordID) {
		this.wordID = wordID;
	}

}
