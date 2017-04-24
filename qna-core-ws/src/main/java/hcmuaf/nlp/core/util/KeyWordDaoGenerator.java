/**
 * @author Manh Phan
 *
 * Edited date Mar 4, 2017
 */
package hcmuaf.nlp.core.util;

import hcmuaf.nlp.core.dao.KeyWordDao;
import hcmuaf.nlp.core.hibernateDao.impl.KeyWordDaoImpl;

/**
 * The Class KeyWordDaoGenerator.
 */
public class KeyWordDaoGenerator {
	
	/** The key word dao. */
	private static KeyWordDao keyWordDao;

	/**
	 * Build the keyword dao.
	 *
	 * @return the keyword dao
	 */
	private static KeyWordDao buildKeyWordDao() {
		keyWordDao = new KeyWordDaoImpl();
		return keyWordDao;
	}

	/**
	 * Gets the current keyword dao.
	 *
	 * @return the current keyword dao
	 */
	public static KeyWordDao getCurrentKeyWordDao() {
		if (keyWordDao == null) {
			keyWordDao = buildKeyWordDao();
		}
		return keyWordDao;
	}
}
