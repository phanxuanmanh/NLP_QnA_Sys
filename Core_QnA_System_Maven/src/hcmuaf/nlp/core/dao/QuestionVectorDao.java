package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.QuestionVector;

import java.util.ArrayList;

public interface QuestionVectorDao {
	public void updateTFIDF(int qid, int wid, double tfidf);

	public ArrayList<QuestionVector> getListVector(int questionID);

	public void updateWordCount(int questionId, int wordId);

	public int numOfQuestionContainWord(int wid);
}
