/**
 * @author Manh Phan
 *
 * Edited date Mar 4, 2017
 */
package hcmuaf.nlp.rest.resource;

import hcmuaf.nlp.core.dto.QnAPair;
import hcmuaf.nlp.core.model.Answer;
import hcmuaf.nlp.core.model.Question;
import hcmuaf.nlp.core.service.QnAProcessService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class QnAController.
 */
@RestController
public class QnAController {
	
	/** The process service. */
	@Autowired
	private QnAProcessService processService;

	/**
	 * Gets the relate qn A.
	 *
	 * @param question the question
	 * @return the relate qn A
	 */
	@RequestMapping(value = "/qnas/", method = RequestMethod.POST)
	public ResponseEntity<List<QnAPair>> getRelateQnA(@RequestBody String question) {
		List<QnAPair> qnas = processService.getListRelateQnA(question);
		if (CollectionUtils.isEmpty(qnas)) {
			return new ResponseEntity<List<QnAPair>>(HttpStatus.NO_CONTENT);
		}
		System.out.println("this is the list");
		return new ResponseEntity<List<QnAPair>>(qnas, HttpStatus.OK);
	}

	/**
	 * Insert answer.
	 *
	 * @param questionId the question id
	 * @param answer the answer
	 * @return the response entity
	 */
	@RequestMapping(value = "/qna/answer/add/{sid}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> insertAnswer(@PathVariable("id") int questionId, @RequestBody String answer) {
		System.out.println("add answer for question with id " + questionId);
		try {
			processService.addAnswer(questionId, answer);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

	/**
	 * Gets the qnA by id.
	 *
	 * @param questionId the question id
	 * @return the qnA by id
	 */
	@RequestMapping(value = "/qnas/{questionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<QnAPair>> getQnAById(@PathVariable("questionId") long questionId) {
		List<QnAPair> listQnA = processService.getQnAByQuestionId(questionId);
		System.out.println("return " + listQnA.size() + " qna");
		return new ResponseEntity<List<QnAPair>>(listQnA, HttpStatus.OK);
	}

	/**
	 * Gets the answer by question id.
	 *
	 * @param questionId the question id
	 * @return the answer by question id
	 */
	@RequestMapping(value = "/qnas/answer/getByquestionId/{questionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Answer> getAnswerByQuestionId(@PathVariable("questionId") long questionId) {
		Answer answer = processService.getAnswerByQuestionId(questionId).get(0);
		return new ResponseEntity<Answer>(answer, HttpStatus.OK);
	}
	
	/**
	 * Gets the question content by question id.
	 *
	 * @param questionId the question id
	 * @return the question content by question id
	 */
	@RequestMapping(value = "/qnas/question/getByquestionId/{questionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Question> getQuestionContentByQuestionId(@PathVariable("questionId") long questionId) {
		Question question = processService.getQuestionById(questionId);
		return new ResponseEntity<Question>(question, HttpStatus.OK);
	}

}