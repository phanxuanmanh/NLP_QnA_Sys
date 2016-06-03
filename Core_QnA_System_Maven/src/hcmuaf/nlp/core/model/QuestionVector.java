package hcmuaf.nlp.core.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "QUESTION_VECTORS")
public class QuestionVector {
	@EmbeddedId
	private QuestionWordPair qwPair;
	@Column(name = "freq")
	private int freq;
	@Column(name = "weight")
	private double tfidf;

	public QuestionVector(int questionID, int wordID, int freq, double tfidf) {
		super();
		QuestionWordPair pair = new QuestionWordPair();
		pair.setQuestionID(questionID);
		pair.setWordID(wordID);
		this.qwPair = pair;
		this.freq = freq;
		this.tfidf = tfidf;
	}

	public QuestionVector() {
		qwPair = new QuestionWordPair();
	}

	public int getQuestionID() {
		return qwPair.getQuestionID();
	}

	public void setQuestionID(int questionID) {
		this.qwPair.setQuestionID(questionID);
	}

	public int getWordID() {
		return qwPair.getWordID();
	}

	public void setWordID(int wordID) {
		this.qwPair.setWordID(wordID);
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
