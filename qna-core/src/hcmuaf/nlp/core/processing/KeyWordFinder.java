/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.processing;

import hcmuaf.nlp.core.dao.KeyWordDao;
import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dao.QuestionVectorDao;
import hcmuaf.nlp.core.hibernateDao.impl.QuestionVectorDaoImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import edu.stanford.nlp.ling.WordTag;

/**
 * The Class KeyWordFinder.
 */
public class KeyWordFinder {
	
	/** The tagger. */
	private VietnameseMaxentTagger tagger;
	
	/** The key word dao. */
	private KeyWordDao keyWordDao;
	
	/** The question dao. */
	private QuestionDao questionDao;

	/**
	 * Gets the question dao.
	 *
	 * @return the question dao
	 */
	public QuestionDao getQuestionDao() {
		return questionDao;
	}

	/**
	 * Sets the question dao.
	 *
	 * @param questionDao the new question dao
	 */
	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	/**
	 * Instantiates a new key word finder.
	 */
	public KeyWordFinder() {
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
	 * Find words.
	 *
	 * @param filePath the file path
	 */
	public void findWords(String filePath) {
		Set<String> listDBWord = keyWordDao.getListkeyWord();
		String[] sentences = vn.hus.nlp.utils.UTF8FileUtility
				.getLines(filePath);
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

	/**
	 * Question statistic.
	 *
	 * @param quesID the ques id
	 */
	public void questionStatistic(int quesID) {
		String question = questionDao.getQuestion(quesID);
		List<WordTag> listTagWord = new ArrayList<WordTag>();
		HashMap<Integer, Integer> listWordWithFreq = new HashMap<Integer, Integer>();
		if (question != null) {
			String[] parts = question.split("\\.");
			for(String part : parts ){
				if(part.length()>300){
					question =question.replace(part, ".");
				}
			}
			long start = System.currentTimeMillis();
			BufferedReader reader = new BufferedReader(new StringReader(
					question));
			String line = "";
			try {
				while ((line = reader.readLine()) != null) {
					line = line.replaceAll("<[^>]+>", " ");
					if (line.trim().length() > 1 ) {
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
							Integer wid = Integer.valueOf(keyWordDao
									.getWordId(wordContent));
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
			QuestionVectorDao questionVectorDao = new QuestionVectorDaoImpl();
			questionVectorDao.saveWordCount(listWordWithFreq, quesID);

			long end = System.currentTimeMillis();
			System.out.println("Finish on question " + quesID
					+ " :  with time in ms " + (end - start));
		}
	}

}
