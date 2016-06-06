package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.WikiConceptWord;

import java.util.HashMap;
import java.util.List;

public interface WikiConceptVectorDao {
	public long numberOfConceptContainWord(int wordId);

	public List<WikiConceptWord> listWordVector(int page);
	
	public List<WikiConceptWord> listWordVectorByWordId(int wordId);
	
	public List<WikiConceptWord> listWordVectorByListWordId(List<Integer> listWordIds);
	
	public long currentNumberOfConcept();

	public List<Integer> getListPageId();

	public void updateConceptWord(WikiConceptWord w);
	
	public void updateListConceptWord(List<WikiConceptWord> listWordVectors);
	
	public long numberOfWordInPage(int pageId);

	public HashMap<Integer, Integer> getWordWithFreq();
}
