/**
 * @author Manh Phan
 *
 * Edited date Jun 24, 2016
 */
package hcmuaf.nlp.core.service;

import hcmuaf.nlp.core.dto.QnAPair;
import hcmuaf.nlp.core.model.Question;

import java.util.List;

/**
 * The Interface QnAProcessService.
 */
public interface QnAProcessService {

	/**
	 * Gets the list relate qna.
	 *
	 * @param question
	 *            the question
	 * @return the list relate qna
	 */
	public List<QnAPair> getListRelateQnA(Question question);

}
