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
	private Long id;
	
	/** The page id. */
	@Column(name = "page_id")
	private Integer pageId;
	
	/** The word id. */
	@Column(name = "word_id")
	private Long wordId;
	
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
	


	public WikiConceptWord(Long id, Integer pageId, Long wordId, Integer freq, Double tfidf) {
		super();
		this.id = id;
		this.pageId = pageId;
		this.wordId = wordId;
		this.freq = freq;
		this.tfidf = tfidf;
	}



	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
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
	public Long getWordId() {
		return wordId;
	}

	/**
	 * Sets the word id.
	 *
	 * @param wordId the new word id
	 */
	public void setWordId(Long wordId) {
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
