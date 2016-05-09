package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.QuestionType;

import java.util.ArrayList;

public interface QuestionTypeDao {
	public ArrayList<QuestionType> getTypeList();
	public  int getQuestionType(int qId);
}
