package hcmuaf.nlp.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "QUESTION_VECTORS")
public class QuestionVector {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "q_id")
	private int questionID;
	@Column(name = "wid")
	private int wordID;
	@Column(name = "freq")
	private int freq;
	@Column(name = "weight")
	private double tfidf;

	public QuestionVector(int questionID, int wordID, int freq, double tfidf) {
		super();
		this.questionID = questionID;
		this.wordID = wordID;
		this.freq = freq;
		this.tfidf = tfidf;
	}

	public QuestionVector() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
