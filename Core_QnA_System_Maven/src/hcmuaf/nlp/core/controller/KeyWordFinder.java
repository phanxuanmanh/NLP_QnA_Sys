package hcmuaf.nlp.core.controller;
import hcmuaf.nlp.core.dao.KeyWordDao;
import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.jdbcDao.impl.KeyWordDaoImpl;
import hcmuaf.nlp.core.jdbcDao.impl.QuestionDaoImpl;

import java.util.List;
import java.util.Set;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import edu.stanford.nlp.ling.WordTag;

public class KeyWordFinder {
	private static final String[] listTag = { "Np", "Nc", "Nu", "N", "V", "A",
			"P", "M", "E", "C", "CC", "I", "T", "X", "Y", "Z" };

	KeyWordDao keyWordDao = new KeyWordDaoImpl();
	public void findWords(String filePath) {
		Set<String> listDBWord = keyWordDao.getListkeyWord();
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
							keyWordDao.addKeyWord(wordTag.word());
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
		QuestionDao questionDao = new QuestionDaoImpl();
		String question = questionDao.getQuestion(quesID);
		/**
		 * needto improve this function, see WikiWordFinder
		 * **/
		if (question != null) {
			String[] quesArr = question.split("\\.");
			for (String str : quesArr) {
				if (str.length() > 300)
					continue;
				VietnameseMaxentTagger tagger = new VietnameseMaxentTagger();
				try {
					System.out.println("complete split");
					List<WordTag> list = tagger.tagText2(str);
					for (WordTag wordTag : list) {
						if (isKeyWord(wordTag)) {
							String wordContent = wordTag.word();
							int wid = keyWordDao.getWordId(wordContent);
							questionDao.updateWordCount(quesID, wid);
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
