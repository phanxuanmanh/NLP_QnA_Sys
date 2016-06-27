/**
 * @author Manh Phan
 *
 * Edited date Jun 25, 2016
 */
package hcmuaf.nlp.core.service;

import hcmuaf.nlp.core.dto.PredictType;


/**
 * The Interface QnAClassifyService.
 */
public interface QnAClassifyService {
	
	/**
	 * Gets the predict type.
	 *
	 * @param questionId the question id
	 * @return the predict type
	 */
	public PredictType getPredictType(Integer questionId);
}
