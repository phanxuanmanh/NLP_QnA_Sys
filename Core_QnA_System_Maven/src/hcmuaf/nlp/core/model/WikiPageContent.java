package hcmuaf.nlp.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEXT")
public class WikiPageContent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "old_id")
	private int pageLatest;
	@Column(name = "old_text")
	private String text;

	public int getPageLatest() {
		return pageLatest;
	}

	public void setPageLatest(int pageLatest) {
		this.pageLatest = pageLatest;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
