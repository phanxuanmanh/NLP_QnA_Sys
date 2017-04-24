/**
 * @author Manh Phan
 *
 * Edited date Mar 4, 2017
 */
package hcmuaf.nlp.rest.resource;

import hcmuaf.nlp.core.dto.QnAPair;
import hcmuaf.nlp.core.model.CommonQuestionSearchCriteria;
import hcmuaf.nlp.core.model.QuestionHistory;
import hcmuaf.nlp.core.service.QnAHistoryService;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class QuestionHistoryController.
 */
@RestController
public class QuestionHistoryController {
	
	/** The qna history service. */
	@Autowired
	private QnAHistoryService qnaHistoryService;
	
	/**
	 * Gets the history.
	 *
	 * @param searchCriteria the search criteria
	 * @return the history
	 */
	@RequestMapping(value = "/history/common/", method = RequestMethod.POST)
	public ResponseEntity<List<QnAPair>> getHistory(@RequestBody CommonQuestionSearchCriteria searchCriteria) {
		System.out.println("get common qna");
		List<QnAPair> listQnA = qnaHistoryService.getQuestionHistory(searchCriteria.getStartSearchDate(), searchCriteria.getTypeName(), searchCriteria.getKeyWords(), searchCriteria.getFetchSize());
		System.out.println("return " + listQnA.size() + " qna");
		return new ResponseEntity<List<QnAPair>>(listQnA, HttpStatus.OK);
	}
	
	/**
	 * Insert history.
	 *
	 * @param history the history
	 * @return the response entity
	 */
	@RequestMapping(value = "/history/add/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> insertHistory(@RequestBody QuestionHistory history) {
		System.out.println("add history");
		try {
			qnaHistoryService.addQuestionHistory(history);
		} catch (Exception e) {
			throw e;
		}
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	

	/**
	 * Update history.
	 *
	 * @param history the history
	 * @return the response entity
	 */
	@RequestMapping(value = "/history/update", method = RequestMethod.PUT)
	public ResponseEntity<String> updateHistory(@RequestBody QuestionHistory history) {
		try {
			qnaHistoryService.updateHistory(history);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Delete history.
	 *
	 * @param historyId the history id
	 * @return the response entity
	 */
	@RequestMapping(value = "/history/delete/{historyId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteHistory(@PathVariable("historyId") long historyId) {
		try {
			qnaHistoryService.deleteHistory(historyId);
			return new ResponseEntity<String>(HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Gets the qn A history by user.
	 *
	 * @param userId the user id
	 * @return the qn A history by user
	 */
	@RequestMapping(value = "/history/getQnAByUser/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<QnAPair>> getQnAHistoryByUser(@PathVariable("userId") int userId) {
		try {
			List<QnAPair> userQnAHistory = qnaHistoryService.getUserQnAHistory(userId);
			return new ResponseEntity<List<QnAPair>>(userQnAHistory,HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<QnAPair>>(Collections.emptyList(),HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
