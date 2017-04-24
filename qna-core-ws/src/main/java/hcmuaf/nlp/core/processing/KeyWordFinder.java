/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.processing;

import hcmuaf.nlp.core.dao.KeyWordDao;
import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dao.QuestionVectorDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import edu.stanford.nlp.ling.WordTag;

/**
 * The Class KeyWordFinder.
 */

@Service("keywordFinder")
public class KeyWordFinder {
	
	/** The tagger. */
	private VietnameseMaxentTagger tagger;
	
	/** The keyWord dao. */
	@Autowired
	private KeyWordDao keyWordDao;
	
	/** The question dao. */
	@Autowired
	private QuestionDao questionDao;
	
	/** The questionVector dao. */
	@Autowired
	private QuestionVectorDao questionVectorDao;


	/**
	 * Instantiates a new key word finder.
	 */
	public KeyWordFinder() {
		System.out.println("Current file URL :" + System.getProperty("user.dir"));
		tagger = new VietnameseMaxentTagger();
	}

	/**
	 * Find words.
	 *
	 * @param filePath the file path
	 */
	public void findWords(String filePath) {
		Set<String> listDBWord = keyWordDao.getListKeyWordContent();
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
	 * Find key word for question.
	 *
	 * @param questionID the question ID
	 */
	public void findKeyWordForQuestion(int questionID) {
		Logger.getLogger(this.getClass()).log(Level.ERROR, "Current file URL :" + System.getProperty("user.dir"));
		String question = questionDao.getQuestionContent(questionID);
		List<WordTag> listTagWord = new ArrayList<WordTag>();
		if (question != null) {
			String[] parts = question.split("\\.");
			for(String part : parts ){
				if(part.length()>300){
					question =question.replace(part, ".");
				}
			}
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
			saveKeyWordOccurence(questionID, listTagWord);
		}
	}
	
	/**
	 * Find key word.
	 *
	 * @param inputTexts the input texts
	 * @return the list
	 */
	public List<WordTag> findKeyWord(String[] inputTexts){
		List<WordTag> listTagWord = new ArrayList<WordTag>();
		for(String text : inputTexts){
			text = text.replaceAll("<[^>]+>", " ");
			listTagWord.addAll( tagger.tagText2(text));
		}
		return listTagWord;
	}
	
	/**
	 * Save key word occurrence.
	 *
	 * @param questionID the question ID
	 * @param listTagWord the list tag word
	 */
	private void saveKeyWordOccurence(long questionID, List<WordTag> listTagWord) {
		ConcurrentMap<Long, Long> wordOccurents = listTagWord.stream().map(WordTag::word)
				.filter(StringUtils::isNotBlank).filter(word -> word.length() < 500)
				.map(word -> keyWordDao.getOrSaveKeyWord(word))
				.collect(Collectors.groupingByConcurrent(Function.identity(), Collectors.counting()));
		questionVectorDao.saveWordCount(wordOccurents, questionID);
	}

}
