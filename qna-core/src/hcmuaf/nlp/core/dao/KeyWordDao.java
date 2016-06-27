/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.Keyword;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * The Interface KeyWordDao.
 */
public interface KeyWordDao {
	
	/**
	 * Gets the list word.
	 *
	 * @return the list word
	 */
	public  HashMap<String, Integer> getListWord();
	
	/**
	 * Gets the listkey word.
	 *
	 * @return the listkey word
	 */
	public Set<String> getListkeyWord();
	
	/**
	 * Gets the listkey word2.
	 *
	 * @return the listkey word2
	 */
	public ArrayList<Keyword> getListkeyWord2();
	
	/**
	 * Gets the listkey word.
	 *
	 * @param wcontent the wcontent
	 * @return the listkey word
	 */
	public ArrayList<Keyword> getListkeyWord(String wcontent);
	
	/**
	 * Adds the key word.
	 *
	 * @param keyWord the key word
	 * @return the integer
	 * @throws SQLException the SQL exception
	 */
	public Integer addKeyWord(String keyWord) throws SQLException;
	
	/**
	 * Gets the word id.
	 *
	 * @param keyword the keyword
	 * @return the word id
	 * @throws SQLException the SQL exception
	 */
	public Integer getWordId(String keyword) throws SQLException;
	
	/**
	 * Gets the key word.
	 *
	 * @param wid the wid
	 * @return the key word
	 */
	public Keyword getKeyWord(int wid);
}
