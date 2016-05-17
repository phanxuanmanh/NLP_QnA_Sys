package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.QuestionVectorCsv;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QuestionDao {
	public ArrayList<Integer> getQuestionList();
	public  int countQuestion();
	public int getQuestionType(int qId);

	public String getQuestion(int qId);

	public int insertQuestion(String question,int typeID);

	public QuestionVectorCsv readQuestionVectorData(int questionID) throws SQLException;

	public ArrayList<QuestionVectorCsv> readQuestionVectorData() throws SQLException;
	public void updateWordCount(int questionId, int wordId);
	public  int numOfQuestionContainWord(int wid);
}
