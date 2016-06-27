/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.WikiInvertedIndex;

import java.util.HashMap;
import java.util.List;

/**
 * The Interface WikiInvertedIndexDao.
 */
public interface WikiInvertedIndexDao {
	
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
	public List<WikiInvertedIndex> listWordVector(int page);

	/**
	 * List word vector by word id.
	 *
	 * @param wordId the word id
	 * @return the list
	 */
	public List<WikiInvertedIndex> listWordVectorByWordId(int wordId);

	/**
	 * Gets the all vector.
	 *
	 * @return the all vector
	 */
	public List<WikiInvertedIndex> getAllVector();

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
	
	/**
	 * Current number of vector.
	 *
	 * @return the long
	 */
	public Long currentNumberOfVector();
}
