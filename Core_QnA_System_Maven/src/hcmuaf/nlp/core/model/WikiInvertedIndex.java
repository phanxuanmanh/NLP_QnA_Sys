package hcmuaf.nlp.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "INVERTED_INDEX")
public class WikiInvertedIndex {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "page_id")
	private int pageId;
	@Column(name = "word_id")
	private int wordId;
	@Column(name = "freq")
	private int freq;
	@Column(name = "tfidf")
	private double tfidf;
	
	
	public WikiInvertedIndex() {
		super();
	}

	public WikiInvertedIndex(int id, int pageId, int wordId, int freq,
			double tfidf) {
		super();
		this.id = id;
		this.pageId = pageId;
		this.wordId = wordId;
		this.freq = freq;
		this.tfidf = tfidf;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public WikiConceptWord toWikiConcept(){
		WikiConceptWord obj = new WikiConceptWord();
		obj.setId(id);
		obj.setPageId(pageId);
		obj.setWordId(wordId);
		obj.setTfidf(tfidf);
		obj.setFreq(freq);
		return obj;
		
	}
}
