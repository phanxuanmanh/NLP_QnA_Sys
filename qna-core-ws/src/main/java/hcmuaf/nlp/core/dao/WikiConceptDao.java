/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.WikiPage;

import java.util.ArrayList;

/**
 * The Interface WikiConceptDao.
 */
public interface WikiConceptDao {
	

	/**
	 * Gets the concept list.
	 *
	 * @return the concept list
	 */
	public ArrayList<Integer> getConceptList();

	/**
	 * Gets the concept text.
	 *
	 * @param page_latest the page_latest
	 * @return the concept text
	 */
	public String getConceptText(int page_latest);

	/**
	 * Update word count.
	 *
	 * @param page_latest the page_latest
	 * @param wordId the word id
	 * @param freq the frequency
	 */
	public void updateWordCount(int page_latest, long wordId, int freq);

	/**
	 * Gets the page title.
	 *
	 * @param pageID the page id
	 * @return the page title
	 */
	public String getPageTitle(int pageID);
	
	/**
	 * Gets the page title by reference page id.
	 *
	 * @param pageID the page ID
	 * @return the page title by reference page id
	 */
	public String getPageTitleByRefPageId(int pageID);

	/**
	 * Gets the page.
	 *
	 * @param pageId the page id
	 * @return the page
	 */
	public WikiPage getPage(int pageId);
	
	/**
	 * Update page.
	 *
	 * @param wikiPage the wiki page
	 */
	public void updatePage(WikiPage wikiPage);
}
