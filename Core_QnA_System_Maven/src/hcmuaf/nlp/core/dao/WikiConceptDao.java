package hcmuaf.nlp.core.dao;

import java.util.ArrayList;

public interface WikiConceptDao {
	public ArrayList<Integer> getConCeptList();

	public String getConceptText(int page_latest);

	public void updateWordCount(int page_latest, int wordId, int freq);
}
