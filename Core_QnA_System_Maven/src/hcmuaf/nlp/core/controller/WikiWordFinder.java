package hcmuaf.nlp.core.controller;

import java.sql.SQLException;
import java.util.List;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import edu.stanford.nlp.ling.WordTag;
import hcmuaf.nlp.core.dao.KeyWordDao;
import hcmuaf.nlp.core.dao.WikiConceptDao;
import hcmuaf.nlp.core.hibernateDao.impl.KeyWordDaoImpl;
import hcmuaf.nlp.core.hibernateDao.impl.WikiConceptDaoImpl;

public class WikiWordFinder {
	private static final String[] listTag = { "Np", "Nc", "Nu", "N", "V", "A",
			"P", "M", "E", "C", "CC", "I", "T", "X", "Y", "Z" };
	private VietnameseMaxentTagger tagger;

	public WikiWordFinder(VietnameseMaxentTagger tagger) {
		this.tagger = tagger;
	}

	public void conceptStatistic(int page_latest) {
		WikiConceptDao conceptDao = new WikiConceptDaoImpl();
		System.out.println("start on page id: " + page_latest);
		WordCounter counter = new WordCounter(page_latest);
		String concept = conceptDao.getConceptText(page_latest);
		KeyWordDao keyWordDao = new KeyWordDaoImpl();
		if (concept != null) {
			String[] quesArr = concept.split("\\.");
			for (String str : quesArr) {
				if (str.length() > 300) {
					concept.replace(str, "");
				}

			}
			try {

				List<WordTag> list = tagger.tagText2(concept.replaceAll(
						"\\r\\n|\\r|\\n", " ").trim());
				if (list != null) {
					for (WordTag wordTag : list) {
						if (isKeyWord(wordTag)) {
							String wordContent = wordTag.word();
							int wid;
							try {
								wid = keyWordDao.getWordId(wordContent);
								counter.addWord(wid);
							} catch (SQLException e) {
								System.out.println("word is not available : "
										+ wordContent);
							}

						}

					}
				}

			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println("some error in tagging");
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
