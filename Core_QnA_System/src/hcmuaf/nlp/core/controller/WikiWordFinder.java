package hcmuaf.nlp.core.controller;

import java.util.List;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import edu.stanford.nlp.ling.WordTag;
import hcmuaf.nlp.core.DBConnect.WikiContentAccessor;
import hcmuaf.nlp.core.DBConnect.WordAccessor;

public class WikiWordFinder {
	private static final String[] listTag = { "Np", "Nc", "Nu", "N", "V", "A",
			"P", "M", "E", "C", "CC", "I", "T", "X", "Y", "Z" };
	private static WordAccessor wordAccess;

	public WikiWordFinder() {
		wordAccess = new WordAccessor();
	}

	public void conceptStatistic(int page_latest) {
		WordCounter counter = new WordCounter(page_latest);
		VietnameseMaxentTagger tagger = new VietnameseMaxentTagger();
		String concept = WikiContentAccessor.getConceptText(page_latest);
		if (concept != null) {
			String[] quesArr = concept.split("\\.");
			for (String str : quesArr) {
				if (str.length() > 300)
					continue;
				try {
					if (str.replaceAll("\\r\\n|\\r|\\n", " ").trim().length() > 2) {
						List<WordTag> list = tagger.tagText2(str.replaceAll(
								"\\r\\n|\\r|\\n", " ").trim());
						/*
						 * for (WordTag wordTag : list) { if
						 * (!wordTag.tag().equals("R") &&
						 * !wordTag.tag().equals("L")) { if
						 * (!listDBWord.contains(wordTag.word().toUpperCase()))
						 * { listDBWord.add(wordTag.word().toUpperCase());
						 * wordAccess.addKeyWord(wordTag.word()); } int wid =
						 * wordAccess.getWordId(wordTag.word());
						 * wordAccess.updateWordCount(quesID, wid); } }
						 */
						for (WordTag wordTag : list) {
							if (isKeyWord(wordTag)) {
								String wordContent = wordTag.word();
								int wid = wordAccess.getWordId(wordContent);
								counter.addWord(wid);

							}

						}
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
			counter.updateWordCount();
		}
	}

	public boolean isKeyWord(WordTag wordTag) {
		for (String s : listTag)
			if (wordTag.tag().endsWith(s))
				return true;
		return false;
	}
}
