package hcmuaf.nlp.core.model;

public class WordCounterItem {
	private int wordID;
	private int freq;

	public WordCounterItem(int wordID, int freq) {
		super();
		this.wordID = wordID;
		this.freq = freq;
	}

	public int getWordID() {
		return wordID;
	}
	
	public void setWordID(int wordID) {
		this.wordID = wordID;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

}
