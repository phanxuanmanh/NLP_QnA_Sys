package hcmuaf.nlp.core.controller;

import hcmuaf.nlp.core.DBConnect.QnAAccessor;
import hcmuaf.nlp.core.DBConnect.WordAccessor;

import java.util.List;
import java.util.Set;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import edu.stanford.nlp.ling.WordTag;

public class KeyWordFinder {
	private static WordAccessor wordAccess;

	public void findWords(String filePath) {
		wordAccess = new WordAccessor();
		Set<String> listDBWord = wordAccess.getListkeyWord();
		String[] sentences = vn.hus.nlp.utils.UTF8FileUtility
				.getLines(filePath);

		VietnameseMaxentTagger tagger = new VietnameseMaxentTagger();
		for (String sentence : sentences) {
			try {
				List<WordTag> list = tagger.tagText2(sentence);
				for (WordTag wordTag : list) {
					if (!wordTag.tag().equals("R")
							&& !wordTag.tag().equals("L")) {
						if (!listDBWord.contains(wordTag.word().toUpperCase())) {
							listDBWord.add(wordTag.word().toUpperCase());
							wordAccess.addKeyWord(wordTag.word());
						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("completed find keyword on file : " + filePath);
	}

	public void questionStatistic(int quesID) {
		String question = QnAAccessor.getQuestion(quesID);
		if (question != null) {
			wordAccess = new WordAccessor();

			Set<String> listDBWord = wordAccess.getListkeyWord();
			VietnameseMaxentTagger tagger = new VietnameseMaxentTagger();
			try {
				StringBuffer result = new StringBuffer(1024);
				List<WordTag> list = tagger.tagText2(question);
				System.out.println("start");
				for (WordTag wordTag : list) {
					if (!wordTag.tag().equals("R")
							&& !wordTag.tag().equals("L")) {
						if (!listDBWord.contains(wordTag.word().toUpperCase())) {
							listDBWord.add(wordTag.word().toUpperCase());
							wordAccess.addKeyWord(wordTag.word());
						}
						int wid = wordAccess.getWordId(wordTag.word());
						wordAccess.updateWordCount(quesID, wid);
					}
				}
				System.out.println(result.toString().trim());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("completed work on question with ID : " + quesID);
	}
}
