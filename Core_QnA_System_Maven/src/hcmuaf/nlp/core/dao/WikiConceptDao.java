package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.WikiPage;

import java.util.ArrayList;

public interface WikiConceptDao {
	public ArrayList<Integer> getConCeptList();

	public String getConceptText(int page_latest);

	public void updateWordCount(int page_latest, int wordId, int freq);

	public String getPageTitle(int pageID);

	public WikiPage getPage(int pageId);
	
	public void updatePage(WikiPage wikiPage);
}
