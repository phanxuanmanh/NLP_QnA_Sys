package main.demo.tokenizer;

import java.util.List;
import java.util.Set;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import edu.stanford.nlp.ling.WordTag;
import main.demo.DBConnect.QnA_Accessor;
import main.demo.DBConnect.WordAccessor;

public class KeyWordFinder {
	private static WordAccessor wordAccess;

	public void findWords(String filePath) {
		if (wordAccess != null) {
			wordAccess = new WordAccessor();
		}
		Set<String> listDBWord = wordAccess.getListkeyWord();
		String[] sentences = vn.hus.nlp.utils.UTF8FileUtility
				.getLines(filePath);

		VietnameseMaxentTagger tagger = new VietnameseMaxentTagger();
		for (String sentence : sentences) {
			try {
				StringBuffer result = new StringBuffer(1024);
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
				System.out.println(result.toString().trim());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void questionStatistic(int quesID) {
		String question = QnA_Accessor.getQuestion(quesID);
		if (question != null) {
			wordAccess = new WordAccessor();

			Set<String> listDBWord = wordAccess.getListkeyWord();
			VietnameseMaxentTagger tagger = new VietnameseMaxentTagger();
			try {
				StringBuffer result = new StringBuffer(1024);
				List<WordTag> list = tagger.tagText2(question);
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

	}
}
