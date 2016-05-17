package hcmuaf.nlp.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ConceptWordPair  implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
@Column(name="page_id")
private int pageId;
@Column(name="word_id")
private int wordId;

public ConceptWordPair() {
	super();
}
public ConceptWordPair(int pageId, int wordId) {
	super();
	this.pageId = pageId;
	this.wordId = wordId;
}
public int getPageId() {
	return pageId;
}
public void setPageId(int pageId) {
	this.pageId = pageId;
}
public int getWordId() {
	return wordId;
}
public void setWordId(int wordId) {
	this.wordId = wordId;
}

}
