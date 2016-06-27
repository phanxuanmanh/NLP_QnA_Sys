/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.dto.QnAPair;

/**
 * The Interface QnADao.
 */
public interface QnADao {
	
	/**
	 * Insert qn a pair.
	 *
	 * @param pair the pair
	 */
	public void insertQnAPair(QnAPair pair);
}
