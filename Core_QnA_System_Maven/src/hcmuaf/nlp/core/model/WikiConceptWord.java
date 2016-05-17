package hcmuaf.nlp.core.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CONCEPTS_WORDS")
public class WikiConceptWord {
	@EmbeddedId
	private ConceptWordPair cwPair;
	@Column(name = "freq")
	private int freq;
	@Column(name = "tfidf")
	private double tfidf;

	public WikiConceptWord() {
		super();
	}

	public ConceptWordPair getCwPair() {
		return cwPair;
	}

	public void setCwPair(ConceptWordPair cwPair) {
		this.cwPair = cwPair;
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

}
