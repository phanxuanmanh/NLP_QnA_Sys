package hcmuaf.nlp.core.wiki;

import hcmuaf.nlp.core.controller.WordCounter;
import hcmuaf.nlp.core.dao.KeyWordDao;
import hcmuaf.nlp.core.dao.WikiConceptDao;
import hcmuaf.nlp.core.hibernateDao.impl.WikiConceptDaoImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import edu.stanford.nlp.ling.WordTag;

public class WikiWordFinder {
	private VietnameseMaxentTagger tagger;
	KeyWordDao keyWordDao;

	public WikiWordFinder() {
	}

	public VietnameseMaxentTagger getTagger() {
		return tagger;
	}

	public void setTagger(VietnameseMaxentTagger tagger) {
		this.tagger = tagger;
	}

	public KeyWordDao getKeyWordDao() {
		return keyWordDao;
	}

	public void setKeyWordDao(KeyWordDao keyWordDao) {
		this.keyWordDao = keyWordDao;
	}

	public void conceptStatistic(int page_latest) {
		System.out.println("start on concept " + page_latest);
		WikiConceptDao conceptDao = new WikiConceptDaoImpl();
		WordCounter counter = new WordCounter(page_latest);
		String concept = conceptDao.getConceptText(page_latest);
		List<WordTag> listTagWord = new ArrayList<WordTag>();
		if (concept != null) {
			long start = System.currentTimeMillis();
			BufferedReader reader = new BufferedReader(
					new StringReader(concept));
			String line = "";
			try {
				while ((line = reader.readLine()) != null) {
					line = line.replaceAll("<[^>]+>", " ");
					if (line.trim().length() > 1) {
						try {
							List<WordTag> list = tagger.tagText2(line);
							if (list != null)
								listTagWord.addAll(list);
						} catch (Exception e) {
							System.out.println(e.getMessage());
							continue;
						}

					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (listTagWord != null) {
					for (WordTag wordTag : listTagWord) {
						String wordContent = wordTag.word();
						int wid;
						if (wordContent.replaceAll("\\W", "").trim().length() > 1
								&& wordContent.length() < 500) {
							wid = keyWordDao.getWordId(wordContent);
							counter.addWord(wid);
						}
					}
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println("some error in tagging");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			counter.updateWordCount();
			long end = System.currentTimeMillis();
			System.out.println("Finish on concept " + page_latest
					+ " :  with time in ms " + (end - start));
		}
	}
}
