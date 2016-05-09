package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.QuestionVector;
import hcmuaf.nlp.core.model.QuestionVectorCsv;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QuestionVectorDao {
	public void updateTFIDF(int qid, int wid, double tfidf);

	public ArrayList<QuestionVector> getListVector(int questionID);

	public void updateWordCount(int questionId, int wordId);

	public int numOfQuestionContainWord(int wid);
	public  ArrayList<QuestionVectorCsv> readQuestionVectorData()
			throws SQLException ;
	public  QuestionVectorCsv readQuestionVectorData(int questionID)
			throws SQLException;
}
