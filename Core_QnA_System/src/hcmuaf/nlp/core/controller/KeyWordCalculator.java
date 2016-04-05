package hcmuaf.nlp.core.controller;

import java.util.ArrayList;

import hcmuaf.nlp.core.DBConnect.WordAccessor;
import hcmuaf.nlp.core.model.QuestionVector;

public class KeyWordCalculator {
	public static int numOfQuestion = 0;

	public KeyWordCalculator() {
		numOfQuestion = WordAccessor.countQuestion();
	}

	public void updateIDF(int wid) {
		int questionContainWord = WordAccessor.numOfQuestionContainWord(wid);
		double idf = 0.0;
		if (questionContainWord > 0) {
			idf = Math.log10(numOfQuestion / questionContainWord);
		}
		try {
			if (idf > 0) {
				WordAccessor.updateIDF(idf, wid);
			}
		} catch (Exception e) {
			System.out.println("Error occur when insert");
		}
	}

	public void updateTFIDF(int qid) {
		int totalWord = 0;
		ArrayList<QuestionVector> listVector = WordAccessor.getListVector(qid);
		for (QuestionVector q : listVector) {
			if (q.getFreq() > 0) {
				totalWord += q.getFreq();
			}
		}
		for (QuestionVector q : listVector) {
			double tf = (double)q.getFreq() /totalWord;
			double idf = WordAccessor.getIDF(q.getWordID());
			double tfidf = tf * idf;
			WordAccessor.updateTFIDF(q.getQuestionID(), q.getWordID(), tfidf);

		}
	}

}
