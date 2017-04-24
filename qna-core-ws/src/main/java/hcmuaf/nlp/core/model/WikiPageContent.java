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
 * The Class WikiPageContent.
 */
@Entity
@Table(name = "TEXT")
public class WikiPageContent {
	
	/** The page latest. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "old_id")
	private Integer pageLatest;
	
	/** The text. */
	@Column(name = "old_text")
	private String text;

	/**
	 * Gets the page latest.
	 *
	 * @return the page latest
	 */
	public Integer getPageLatest() {
		return pageLatest;
	}

	/**
	 * Sets the page latest.
	 *
	 * @param pageLatest the new page latest
	 */
	public void setPageLatest(Integer pageLatest) {
		this.pageLatest = pageLatest;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

}
