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

/**
 * The Class WikiConceptWord.
 */
@Entity
@Table(name = "CONCEPTS_WORDS")
public class WikiConceptWord {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	/** The page id. */
	@Column(name = "page_id")
	private Integer pageId;
	
	/** The word id. */
	@Column(name = "word_id")
	private Integer wordId;
	
	/** The freq. */
	@Column(name = "freq")
	private Integer freq;
	
	/** The tfidf. */
	@Column(name = "tfidf")
	private Double tfidf;

	/**
	 * Instantiates a new wiki concept word.
	 */
	public WikiConceptWord() {
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
	 * Gets the page id.
	 *
	 * @return the page id
	 */
	public Integer getPageId() {
		return pageId;
	}

	/**
	 * Sets the page id.
	 *
	 * @param pageId the new page id
	 */
	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	/**
	 * Gets the word id.
	 *
	 * @return the word id
	 */
	public Integer getWordId() {
		return wordId;
	}

	/**
	 * Sets the word id.
	 *
	 * @param wordId the new word id
	 */
	public void setWordId(Integer wordId) {
		this.wordId = wordId;
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
