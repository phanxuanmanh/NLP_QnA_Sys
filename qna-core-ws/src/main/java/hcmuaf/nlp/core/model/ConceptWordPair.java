/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The Class ConceptWordPair.
 */
@Embeddable
public class ConceptWordPair  implements Serializable{

/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

/** The page id. */
@Column(name="page_id")
private Integer pageId;

/** The word id. */
@Column(name="word_id")
private Integer wordId;

/**
 * Instantiates a new concept word pair.
 */
public ConceptWordPair() {
	super();
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

}
