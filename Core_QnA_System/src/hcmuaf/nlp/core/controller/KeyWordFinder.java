package hcmuaf.nlp.core.controller;

import hcmuaf.nlp.core.DBConnect.QnAAccessor;
import hcmuaf.nlp.core.DBConnect.WordAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import edu.stanford.nlp.ling.WordTag;

public class KeyWordFinder {
	private static final String[] listTag = { "Np", "Nc", "Nu", "N", "V", "A",
			"P", "M", "E", "C", "CC", "I", "T", "X", "Y", "Z" };
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
			String[] quesArr = question.split("\\.");
			for (String str : quesArr) {
				if (str.length() > 300)
					continue;
				wordAccess = new WordAccessor();
				VietnameseMaxentTagger tagger = new VietnameseMaxentTagger();
				try {
					System.out.println("complete split");
					List<WordTag> list = tagger.tagText2(str);
					/*
					 * for (WordTag wordTag : list) { if
					 * (!wordTag.tag().equals("R") &&
					 * !wordTag.tag().equals("L")) { if
					 * (!listDBWord.contains(wordTag.word().toUpperCase())) {
					 * listDBWord.add(wordTag.word().toUpperCase());
					 * wordAccess.addKeyWord(wordTag.word()); } int wid =
					 * wordAccess.getWordId(wordTag.word());
					 * wordAccess.updateWordCount(quesID, wid); } }
					 */
					for (WordTag wordTag : list) {
						if (isKeyWord(wordTag)) {
							String wordContent = wordTag.word();
							int wid = wordAccess.getWordId(wordContent);
							wordAccess.updateWordCount(quesID, wid);
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean isKeyWord(WordTag wordTag) {
		for (String s : listTag)
			if (wordTag.tag().endsWith(s))
				return true;
		return false;
	}
}
