/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.processing;

import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dao.QuestionVectorDao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class QuestionWordAccessor.
 */
@Service
public class QuestionWordAccessorImpl implements QuestionWordAccessor {

	/** The number of question. */
	private static int numberOfQuestion = 0;

	/** The list word frequency. */
	private static Map<Long, Integer> listWordFreq;
	
	/** The vector dao. */
	@Autowired
	private  QuestionVectorDao vectorDao;
	
	/** The question dao. */
	@Autowired
	private static QuestionDao questionDao;
	
	

	/**
	 * Gets the number of question.
	 *
	 * @return the number of question
	 */
	public static int getNumberOfQuestion() {
		if (numberOfQuestion == 0) {
			numberOfQuestion = questionDao.countQuestion();
		}
		return numberOfQuestion;
	}

	/**
	 * Gets the list word frequency.
	 *
	 * @return the list word frequency
	 */
	@Override
	public  Map<Long, Integer> getListWordFreq() {
		if (listWordFreq == null) {
			listWordFreq = vectorDao.getWordWithFreq();
			System.out.println("load list Word With Freq");
		}
		return listWordFreq;
	}

	/**
	 * Gets the number of question contain word.
	 *
	 * @param wordId
	 *            the word id
	 * @return the number of question contain word
	 */
	@Override
	public  int getNumberOfQuestionContainWord(long wordId) {
		Integer numberOfQuestion = getListWordFreq().get(wordId);
		if (numberOfQuestion != null) {
			return numberOfQuestion.intValue();
		}
		return 0;
	}


}
