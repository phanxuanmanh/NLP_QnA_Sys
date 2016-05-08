package hcmuaf.nlp.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "QUESTION_VECTORS")
public class QuestionVector {
	@Column(name = "q_id")
	private int questionID;
	@Column(name = "wid")
	private int wordID;
	@Column(name = "weight")
	private int freq;
	@Column(name = "freq")
	private double tfidf;

	public QuestionVector(int questionID, int wordID, int freq, double tfidf) {
		super();
		this.questionID = questionID;
		this.wordID = wordID;
		this.freq = freq;
		this.tfidf = tfidf;
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

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public double getTfidf() {
		return tfidf;
	}

	public void setTfidf(double tfidf) {
		this.tfidf = tfidf;
	}

}
