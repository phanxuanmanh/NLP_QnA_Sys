package hcmuaf.nlp.core.controller;

import java.util.ArrayList;
import hcmuaf.nlp.core.dao.KeyWordDao;
import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dao.QuestionVectorDao;
import hcmuaf.nlp.core.jdbcDao.impl.KeyWordDaoImpl;
import hcmuaf.nlp.core.jdbcDao.impl.QuestionDaoImpl;
import hcmuaf.nlp.core.jdbcDao.impl.QuestionVectorDaoImpl;
import hcmuaf.nlp.core.model.QuestionVector;

public class KeyWordCalculator {
	public static int numOfQuestion = 0;
	QuestionDao questionDao = new QuestionDaoImpl();
	KeyWordDao keyWordDao = new KeyWordDaoImpl();
	QuestionVectorDao vectorDao = new QuestionVectorDaoImpl();
	public KeyWordCalculator() {
		numOfQuestion = questionDao.countQuestion();
	}

	public void updateIDF(int wid) {
		int questionContainWord = questionDao.numOfQuestionContainWord(wid);
		double idf = 0.0;
		if (questionContainWord > 0) {
			idf = Math.log10(numOfQuestion / questionContainWord);
		}
		try {
			if (idf > 0) {
				keyWordDao.updateIDF(idf, wid);
			}
		} catch (Exception e) {
			System.out.println("Error occur when insert");
		}
	}

	public void updateTFIDF(int qid) {
		int totalWord = 0;
		ArrayList<QuestionVector> listVector = vectorDao.getListVector(qid);
		for (QuestionVector q : listVector) {
			if (q.getFreq() > 0) {
				totalWord += q.getFreq();
			}
		}
		for (QuestionVector q : listVector) {
			double tf = (double) q.getFreq() / totalWord;
			double idf = keyWordDao.getIDF(q.getWordID());
			double tfidf = tf * idf;
			vectorDao.updateTFIDF(q.getQuestionID(), q.getWordID(), tfidf);

		}
	}

}
