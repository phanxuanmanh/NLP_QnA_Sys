/**
 * @author Manh Phan
 *
 * Edited date Mar 4, 2017
 */
package hcmuaf.nlp.rest.resource;

import hcmuaf.nlp.core.model.QnASystemConfig;
import hcmuaf.nlp.core.model.QuestionHistory;
import hcmuaf.nlp.core.model.UnAnsweredQnA;
import hcmuaf.nlp.core.service.QnAManageService;
import hcmuaf.nlp.core.service.QnASystemConfigService;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class QuestionManageController.
 */
@RestController
public class QuestionManageController {
	
	/** The manage service. */
	@Autowired
	private QnAManageService manageService;
	
	/** The qnA system configuration service. */
	@Autowired
	private QnASystemConfigService qnASystemConfigService;
	
	/**
	 * Gets the recent un answer.
	 *
	 * @param from the from
	 * @return the recent un answer
	 */
	@RequestMapping(value = "/manage/unanswer", method = RequestMethod.POST)
	public ResponseEntity<List<UnAnsweredQnA>> getRecentUnAnswer(@RequestBody Date from) {
		try {
			List<UnAnsweredQnA> listHistory = manageService.getRecentUnAnswerQuestion(from);
			ResponseEntity<List<UnAnsweredQnA>> response = new ResponseEntity<List<UnAnsweredQnA>>(listHistory,
					HttpStatus.OK);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Gets the recent qn A.
	 *
	 * @param from the from
	 * @param size the size
	 * @param first the first
	 * @return the recent qn A
	 */
	@RequestMapping(value = "/manage/recent", method = RequestMethod.POST)
	public ResponseEntity<List<QuestionHistory>> getRecentQnA(@RequestBody Date from,
			@RequestParam(name = "size") int size, @RequestParam(name = "first") int first) {
		try {
			List<QuestionHistory> listHistory = manageService.getRecentQnA(from, size, first);
			ResponseEntity<List<QuestionHistory>> response = new ResponseEntity<List<QuestionHistory>>(listHistory,
					HttpStatus.OK);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Add the answer.
	 *
	 * @param qna the qna
	 * @return the response entity
	 */
	@RequestMapping(value = "/manage/answer/add/", method = RequestMethod.PUT)
	public ResponseEntity<Long> addAnswer(@RequestBody UnAnsweredQnA qna) {
		try {
			Long answerId = manageService.addAnswer(qna);
			return new ResponseEntity<Long>(answerId, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Gets the all config.
	 *
	 * @return the all config
	 */
	@RequestMapping(value = "/manage/config/", method = RequestMethod.GET)
	public ResponseEntity<List<QnASystemConfig>> getAllConfig() {
		try {
			return new ResponseEntity<List<QnASystemConfig>>(qnASystemConfigService.getAllConfig(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<QnASystemConfig>>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Update config.
	 *
	 * @param config the config
	 * @return the response entity
	 */
	@RequestMapping(value = "/manage/config/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateConfig(@RequestBody QnASystemConfig config) {
		try {
			qnASystemConfigService.updateConfig(config);
			return new ResponseEntity<String>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Update.
	 *
	 * @param configName the config name
	 * @return the response entity
	 */
	@RequestMapping(value = "/manage/config/{configName}", method = RequestMethod.GET)
	public ResponseEntity<QnASystemConfig> update(@PathVariable("configName") String configName) {
		try {
			return new ResponseEntity<QnASystemConfig>(qnASystemConfigService.getConfigByName(configName), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<QnASystemConfig>(new QnASystemConfig(), HttpStatus.BAD_REQUEST);
		}
	}
}
