package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.Keyword;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public interface KeyWordDao {
	public  HashMap<String, Integer> getListWord();
	public  void setListWord(HashMap<String, Integer> listWord);
	public Set<String> getListkeyWord();
	public ArrayList<Keyword> getListkeyWord(String wcontent);
	public int addKeyWord(String keyWord);
	public int getWordId(String keyword);
	public  int numOfQuestionContainWord(int wid);
	public  void updateIDF(double idf, int wid);
	public  double getIDF(int wid);
}
