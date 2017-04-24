/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.WikiConceptWord;

import java.util.HashMap;
import java.util.List;

/**
 * The Interface WikiConceptVectorDao.
 */
public interface WikiConceptWordDao {
	
	/**
	 * Number of concept contain word.
	 *
	 * @param wordId the word id
	 * @return the long
	 */
	public long numberOfConceptContainWord(int wordId);

	/**
	 * List word vector by page.
	 *
	 * @param pageId the page id
	 * @return the list concept-word
	 */
	public List<WikiConceptWord> listWordVectorByPage(int pageId);
	
	/**
	 * List word vector by word id.
	 *
	 * @param wordId the word id
	 * @return the list concept-word
	 */
	public List<WikiConceptWord> listWordVectorByWordId(int wordId);
	
	/**
	 * List word vector by list word id.
	 *
	 * @param listWordIds the list word id
	 * @return the list concept-word
	 */
	public List<WikiConceptWord> listWordVectorByListWordId(List<Integer> listWordIds);
	
	/**
	 * Current number of concept.
	 *
	 * @return the number of concept.
	 */
	public long currentNumberOfConcept();

	/**
	 * Gets the list page id.
	 *
	 * @return the list page id
	 */
	public List<Integer> getListPageId();


	/**
	 * Update concept word.
	 *
	 * @param wikiConceptWord the wiki concept word to update
	 */
	public void updateConceptWord(WikiConceptWord wikiConceptWord);
	
	/**
	 * Update list concept word.
	 *
	 * @param listWordVectors the list word vectors
	 */
	public void updateListConceptWord(List<WikiConceptWord> listWordVectors);
	

	/**
	 * Save word count.
	 *
	 * @param listWordWithFreq the list word with frequency
	 * @param pageId the page id
	 */
	public void saveWordCount(HashMap<Long, Integer> listWordWithFreq,
			int pageId);
	
	/**
	 * Number of word in page.
	 *
	 * @param pageId the page id
	 * @return the long
	 */
	public long numberOfWordInPage(int pageId);

	/**
	 * Gets the word with frequency.
	 *
	 * @return the word with frequency
	 */
	public HashMap<Long, Integer> getWordWithFreq();
	

	/**
	 * Gets the all vector.
	 *
	 * @return  all vector
	 */
	public List<WikiConceptWord> getAllVector();



	
	/**
	 * Current number of vector.
	 *
	 * @return the number of vector
	 */
	public Long currentNumberOfVector();

}
