/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.processing;

import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dao.QuestionVectorDao;
import hcmuaf.nlp.core.hibernateDao.impl.QuestionDaoImpl;
import hcmuaf.nlp.core.hibernateDao.impl.QuestionVectorDaoImpl;

import java.util.HashMap;

/**
 * The Class QuestionWordAccessor.
 */
public class QuestionWordAccessor {
	
	/** The number of question. */
	private static int numberOfQuestion = 0;
	
	/** The list word freq. */
	private static HashMap<Integer, Integer> listWordFreq;

	/**
	 * Gets the number of question.
	 *
	 * @return the number of question
	 */
	public static int getNumberOfQuestion() {
		if (numberOfQuestion == 0) {
			QuestionDao questionDao = new QuestionDaoImpl();
			numberOfQuestion = questionDao.countQuestion();
		}
		return numberOfQuestion;
	}

	/**
	 * Gets the list word freq.
	 *
	 * @return the list word freq
	 */
	public static HashMap<Integer, Integer> getListWordFreq() {
		if (listWordFreq == null) {
			QuestionVectorDao vectorDao = new QuestionVectorDaoImpl();
			listWordFreq = vectorDao.getWordWithFreq();
			System.out.println("load list Word With Freq");
		}
		return listWordFreq;
	}

	/**
	 * Gets the number of question contain word.
	 *
	 * @param wordId the word id
	 * @return the number of question contain word
	 */
	public static int getNumberOfQuestionContainWord(int wordId) {
		return getListWordFreq().get(Integer.valueOf(wordId));
	}
}
