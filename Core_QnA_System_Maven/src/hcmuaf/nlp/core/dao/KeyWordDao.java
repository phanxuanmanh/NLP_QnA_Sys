package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.Keyword;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public interface KeyWordDao {
	public  HashMap<String, Integer> getListWord();
	public Set<String> getListkeyWord();
	public ArrayList<Keyword> getListkeyWord2();
	public ArrayList<Keyword> getListkeyWord(String wcontent);
	public int addKeyWord(String keyWord) throws SQLException;
	public int getWordId(String keyword) throws SQLException;
	public  int numOfQuestionContainWord(int wid);
	public  void updateIDF(double idf, int wid);
	public  double getIDF(int wid);
	public Keyword getKeyWord(int wid);
}
