/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.wiki;

import hcmuaf.nlp.core.dao.KeyWordDao;
import hcmuaf.nlp.core.dao.WikiConceptDao;
import hcmuaf.nlp.core.dao.WikiConceptWordDao;
import hcmuaf.nlp.core.hibernateDao.impl.WikiConceptDaoImpl;
import hcmuaf.nlp.core.hibernateDao.impl.WikiConceptWordDaoImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import edu.stanford.nlp.ling.WordTag;

/**
 * The Class WikiWordFinder.
 */
public class WikiWordFinder {
	
	/** The tagger. */
	private VietnameseMaxentTagger tagger;
	
	/** The key word dao. */
	KeyWordDao keyWordDao;

	/**
	 * Instantiates a new wiki word finder.
	 */
	public WikiWordFinder() {
	}

	/**
	 * Gets the tagger.
	 *
	 * @return the tagger
	 */
	public VietnameseMaxentTagger getTagger() {
		return tagger;
	}

	/**
	 * Sets the tagger.
	 *
	 * @param tagger the new tagger
	 */
	public void setTagger(VietnameseMaxentTagger tagger) {
		this.tagger = tagger;
	}

	/**
	 * Gets the key word dao.
	 *
	 * @return the key word dao
	 */
	public KeyWordDao getKeyWordDao() {
		return keyWordDao;
	}

	/**
	 * Sets the key word dao.
	 *
	 * @param keyWordDao the new key word dao
	 */
	public void setKeyWordDao(KeyWordDao keyWordDao) {
		this.keyWordDao = keyWordDao;
	}

	/**
	 * Concept statistic.
	 *
	 * @param page_latest the page_latest
	 */
	public void conceptStatistic(int page_latest) {
		System.out.println("start on concept " + page_latest);
		WikiConceptDao conceptDao = new WikiConceptDaoImpl();
		String concept = conceptDao.getConceptText(page_latest);
		List<WordTag> listTagWord = new ArrayList<WordTag>();
		HashMap<Long, Integer> listWordWithFreq = new HashMap<Long, Integer>();
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
						if (wordContent.replaceAll("\\W", "").trim().length() > 1
								&& wordContent.length() < 500) {
							Long wid = Long.valueOf(keyWordDao
									.getOrSaveKeyWord(wordContent));
							if (listWordWithFreq.get(wid) != null) {
								Integer freq = listWordWithFreq.get(wid)
										+ new Integer(1);
								listWordWithFreq.put(wid, freq);
							} else {
								listWordWithFreq.put(wid, new Integer(1));
							}
						}
					}
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println("some error in tagging");
			}
			WikiConceptWordDao conceptVectorDao = new WikiConceptWordDaoImpl();
			conceptVectorDao.saveWordCount(listWordWithFreq, page_latest);
			long end = System.currentTimeMillis();
			System.out.println("Finish on concept " + page_latest
					+ " :  with time in ms " + (end - start));
		}
	}
}
