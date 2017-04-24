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
 * The Class WikiPage.
 */
@Entity
@Table(name = "PAGE")
public class WikiPage {
	
	/** The page id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "page_id")
	private Integer pageID;
	
	/** The page latest. */
	@Column(name = "page_latest")
	private Integer pageLatest;
	
	/** The page len. */
	@Column(name = "page_len")
	private Integer pageLen;
	
	/** The title. */
	@Column(name = "page_title")
	private String title;
	
	/** The is redirect. */
	@Column(name = "page_is_redirect")
	private Boolean isRedirect;
	
	/** The page in process. */
	@Column(name = "page_in_process")
	private Boolean pageInProcess;

	/**
	 * Gets the page id.
	 *
	 * @return the page id
	 */
	public Integer getPageID() {
		return pageID;
	}

	/**
	 * Sets the page id.
	 *
	 * @param pageID the new page id
	 */
	public void setPageID(Integer pageID) {
		this.pageID = pageID;
	}

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
	 * Gets the page len.
	 *
	 * @return the page len
	 */
	public Integer getPageLen() {
		return pageLen;
	}

	/**
	 * Sets the page len.
	 *
	 * @param pageLen the new page len
	 */
	public void setPageLen(Integer pageLen) {
		this.pageLen = pageLen;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the checks if is redirect.
	 *
	 * @return the checks if is redirect
	 */
	public Boolean getIsRedirect() {
		return isRedirect;
	}

	/**
	 * Sets the checks if is redirect.
	 *
	 * @param isRedirect the new checks if is redirect
	 */
	public void setIsRedirect(Boolean isRedirect) {
		this.isRedirect = isRedirect;
	}

	/**
	 * Gets the page in process.
	 *
	 * @return the page in process
	 */
	public Boolean getPageInProcess() {
		return pageInProcess;
	}

	/**
	 * Sets the page in process.
	 *
	 * @param pageInProcess the new page in process
	 */
	public void setPageInProcess(Boolean pageInProcess) {
		this.pageInProcess = pageInProcess;
	}

}
