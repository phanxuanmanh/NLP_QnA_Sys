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
public interface WikiConceptVectorDao {
	
	/**
	 * Number of concept contain word.
	 *
	 * @param wordId the word id
	 * @return the long
	 */
	public long numberOfConceptContainWord(int wordId);

	/**
	 * List word vector.
	 *
	 * @param page the page
	 * @return the list
	 */
	public List<WikiConceptWord> listWordVector(int page);
	
	/**
	 * List word vector by word id.
	 *
	 * @param wordId the word id
	 * @return the list
	 */
	public List<WikiConceptWord> listWordVectorByWordId(int wordId);
	
	/**
	 * List word vector by list word id.
	 *
	 * @param listWordIds the list word ids
	 * @return the list
	 */
	public List<WikiConceptWord> listWordVectorByListWordId(List<Integer> listWordIds);
	
	/**
	 * Current number of concept.
	 *
	 * @return the long
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
	 * @param w the w
	 */
	public void updateConceptWord(WikiConceptWord w);
	
	/**
	 * Update list concept word.
	 *
	 * @param listWordVectors the list word vectors
	 */
	public void updateListConceptWord(List<WikiConceptWord> listWordVectors);
	

	/**
	 * Save word count.
	 *
	 * @param listWordWithFreq the list word with freq
	 * @param pageId the page id
	 */
	public void saveWordCount(HashMap<Integer, Integer> listWordWithFreq,
			int pageId);
	
	/**
	 * Number of word in page.
	 *
	 * @param pageId the page id
	 * @return the long
	 */
	public long numberOfWordInPage(int pageId);

	/**
	 * Gets the word with freq.
	 *
	 * @return the word with freq
	 */
	public HashMap<Integer, Integer> getWordWithFreq();

}
