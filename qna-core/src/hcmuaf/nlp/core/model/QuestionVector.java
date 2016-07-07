/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Class QuestionVector.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "QUESTION_VECTORS")
public class QuestionVector {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	/** The question id. */
	@Column(name = "q_id")
	private Integer questionID;
	
	/** The word id. */
	@Column(name = "wid")
	private Integer wordID;
	
	/** The freq. */
	@Column(name = "freq")
	private Integer freq;
	
	/** The tfidf. */
	@Column(name = "weight")
	private Double tfidf;

	/**
	 * Instantiates a new question vector.
	 *
	 * @param questionID the question id
	 * @param wordID the word id
	 * @param freq the freq
	 * @param tfidf the tfidf
	 */
	public QuestionVector(Integer questionID, Integer wordID, Integer freq,
			Double tfidf) {
		super();
		this.questionID = questionID;
		this.wordID = wordID;
		this.freq = freq;
		this.tfidf = tfidf;
	}

	/**
	 * Instantiates a new question vector.
	 */
	public QuestionVector() {
		super();
	}

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
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the question id.
	 *
	 * @return the question id
	 */
	public Integer getQuestionID() {
		return questionID;
	}

	/**
	 * Sets the question id.
	 *
	 * @param questionID the new question id
	 */
	public void setQuestionID(Integer questionID) {
		this.questionID = questionID;
	}

	/**
	 * Gets the word id.
	 *
	 * @return the word id
	 */
	public Integer getWordID() {
		return wordID;
	}

	/**
	 * Sets the word id.
	 *
	 * @param wordID the new word id
	 */
	public void setWordID(Integer wordID) {
		this.wordID = wordID;
	}

	/**
	 * Gets the freq.
	 *
	 * @return the freq
	 */
	public Integer getFreq() {
		return freq;
	}

	/**
	 * Sets the freq.
	 *
	 * @param freq the new freq
	 */
	public void setFreq(Integer freq) {
		this.freq = freq;
	}

	/**
	 * Gets the tfidf.
	 *
	 * @return the tfidf
	 */
	public Double getTfidf() {
		return tfidf;
	}

	/**
	 * Sets the tfidf.
	 *
	 * @param tfidf the new tfidf
	 */
	public void setTfidf(Double tfidf) {
		this.tfidf = tfidf;
	}

}