/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.Keyword;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * The Interface KeyWordDao.
 */
public interface KeyWordDao {

	/**
	 * Gets the map word by content.
	 *
	 * @return the map word by content
	 */
	public  Map<String, Long> getMapWordByContent();
	
	/**
	 * Gets the list key word content.
	 *
	 * @return the list key word content
	 */
	public Set<String> getListKeyWordContent();
	
	/**
	 * Gets The list key word.
	 *
	 * @return The list key word.
	 */
	public ArrayList<Keyword> getListKeyWord();
	
	
	/**
	 * Add the key word.
	 *
	 * @param keyWord The key word.
	 * @return The Id of added keyword.
	 */
	public Long addKeyWord(String keyWord);
	
	/**
	 * Get keyword ID, if not exist, add new.
	 *
	 * @param keyword The keyword.
	 * @return The keyword id.
	 */
	public Long getOrSaveKeyWord(String keyword);
	
	/**
	 * Get the key word id.
	 *
	 * @param keyword the keyword
	 * @return the key word id
	 */
	public Long getKeyWordId(String keyword);
	
}
