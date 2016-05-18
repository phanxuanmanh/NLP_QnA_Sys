package hcmuaf.nlp.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PAGE")
public class WikiPage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "page_id")
	private int pageID;
	@Column(name = "page_latest")
	private int pageLatest;
	@Column(name = "page_len")
	private int pageLen;
	@Column(name = "page_title")
	private String title;
	@Column(name = "page_is_redirect")
	private boolean isRedirect;

	public int getPageID() {
		return pageID;
	}

	public void setPageID(int pageID) {
		this.pageID = pageID;
	}

	public int getPageLatest() {
		return pageLatest;
	}

	public void setPageLatest(int pageLatest) {
		this.pageLatest = pageLatest;
	}

	public int getPageLen() {
		return pageLen;
	}

	public void setPageLen(int pageLen) {
		this.pageLen = pageLen;
	}

	public boolean isRedirect() {
		return isRedirect;
	}

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
