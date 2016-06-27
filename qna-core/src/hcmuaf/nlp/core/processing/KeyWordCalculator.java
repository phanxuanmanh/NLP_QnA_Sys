/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.processing;

import hcmuaf.nlp.core.dao.KeyWordDao;
import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dao.QuestionVectorDao;
import hcmuaf.nlp.core.hibernateDao.impl.KeyWordDaoImpl;
import hcmuaf.nlp.core.hibernateDao.impl.QuestionDaoImpl;
import hcmuaf.nlp.core.hibernateDao.impl.QuestionVectorDaoImpl;
import hcmuaf.nlp.core.model.QuestionVector;

import java.util.List;

/**
 * The Class KeyWordCalculator.
 */
public class KeyWordCalculator {
	
	/** The num of question. */
	public static int numOfQuestion = 0;
	
	/** The question dao. */
	QuestionDao questionDao = new QuestionDaoImpl();
	
	/** The key word dao. */
	KeyWordDao keyWordDao = new KeyWordDaoImpl();
	
	/** The vector dao. */
	QuestionVectorDao vectorDao = new QuestionVectorDaoImpl();

	/**
	 * Instantiates a new key word calculator.
	 */
	public KeyWordCalculator() {
		numOfQuestion = questionDao.countQuestion();
	}

	/**
	 * Calculate tfidf.
	 */
	public void calculateTFIDF() {
		List<Integer> listQuestionId = questionDao.getQuestionList();
		for (int qid : listQuestionId) {
			System.out.println("start on question : "+ qid);
			List<QuestionVector> listVector = vectorDao.getListVector(qid);
			if (listVector.size() > 0) {
				int numberOfWord = 0;
				for (QuestionVector vector : listVector) {
					numberOfWord += vector.getFreq();
				}
				for (QuestionVector vector : listVector) {
					int wordId = vector.getWordID();
					int freq = vector.getFreq();
					int numberOfQuestionContainWord = QuestionWordAccessor
							.getNumberOfQuestionContainWord(wordId);
					double weight = 0;
					if (numberOfQuestionContainWord > 0 && numberOfWord > 0) {
						weight = ((double) freq / numberOfWord)
								* Math.log10((double) numOfQuestion
										/ numberOfQuestionContainWord);
					}
					vector.setTfidf(weight);
				}
				vectorDao.updateListVector(listVector);
			}
		}
	}

}
