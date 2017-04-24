package vn.hcmuaf.nlp.manage.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import vn.hcmuaf.nlp.manage.service.QuestionHistoryService;
import vn.hcmuaf.nlp.ui.model.Answer;
import vn.hcmuaf.nlp.ui.model.Question;
import vn.hcmuaf.nlp.ui.model.QuestionHistory;
import vn.hcmuaf.nlp.ui.model.UnAnsweredQnA;
import vn.hcmuaf.nlp.util.NLPConstants;
import vn.hcmuaf.nlp.util.RestURIProvider;

public class QuestionHistoryServiceImpl implements QuestionHistoryService {
	private static final String FETCH_SIZE = "size";
	private static final String FIRST_RESULT = "first";

	@Override
	public List<QuestionHistory> getListRecentQuestionHistory(Date from, int size, int firstResult)
			throws URISyntaxException {
		RestURIProvider restURIProvider = new RestURIProvider(NLPConstants.HTTP, NLPConstants.SERVICE_HOST,
				NLPConstants.SERVICE_PORT, NLPConstants.QNA_CORE_WS);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair(FETCH_SIZE, String.valueOf(size)));
		parameters.add(new BasicNameValuePair(FIRST_RESULT, String.valueOf(firstResult)));
		URI uri = restURIProvider.getURI(parameters, NLPConstants.RECENT_HISTORY_SERVICE_POSTFIX);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<QuestionHistory[]> responseEntity = restTemplate.postForEntity(uri, from,
				QuestionHistory[].class);
		List<QuestionHistory> responseList = new ArrayList<QuestionHistory>(Arrays.asList(responseEntity.getBody()));
		return responseList;
	}

	@Override
	public List<UnAnsweredQnA> getListUnAnswerQuestion(Date from)
			throws URISyntaxException {
		RestURIProvider restURIProvider = new RestURIProvider(NLPConstants.HTTP, NLPConstants.SERVICE_HOST,
				NLPConstants.SERVICE_PORT, NLPConstants.QNA_CORE_WS);
		URI uri = restURIProvider.getURI(NLPConstants.UNANSWED_HISTORY_SERVICE_POSTFIX);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<UnAnsweredQnA[]> responseEntity = restTemplate.postForEntity(uri, from,
				UnAnsweredQnA[].class);
		List<UnAnsweredQnA> responseList = new ArrayList<UnAnsweredQnA>(Arrays.asList(responseEntity.getBody()));
		return responseList;
	}

	@Override
	public Answer getAnswerByQuestionId(Integer questionId) throws URISyntaxException {
		RestURIProvider restURIProvider = new RestURIProvider(NLPConstants.HTTP, NLPConstants.SERVICE_HOST,
				NLPConstants.SERVICE_PORT, NLPConstants.QNA_CORE_WS);
		URI uri = restURIProvider.getURI(NLPConstants.QNA_SERVICE_PREFIX, NLPConstants.QNA_SERVICE_GET_ANSWER,
				String.valueOf(questionId));
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Answer> responseEntity = restTemplate.getForEntity(uri, Answer.class);
		Answer response = responseEntity.getBody();
		return response;
	}

	@Override
	public Question getContentByQuestionId(int questionId) throws URISyntaxException {
		RestURIProvider restURIProvider = new RestURIProvider(NLPConstants.HTTP, NLPConstants.SERVICE_HOST,
				NLPConstants.SERVICE_PORT, NLPConstants.QNA_CORE_WS);
		URI uri = restURIProvider.getURI(NLPConstants.QNA_SERVICE_PREFIX, NLPConstants.QNA_SERVICE_GET_QUESTION,
				String.valueOf(questionId));
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Question> responseEntity = restTemplate.getForEntity(uri, Question.class);
		Question response = responseEntity.getBody();
		return response;
	}

	@Override
	public void deleteQuestionHistory(int historyId) throws URISyntaxException {
		RestURIProvider restURIProvider = new RestURIProvider(NLPConstants.HTTP, NLPConstants.SERVICE_HOST,
				NLPConstants.SERVICE_PORT, NLPConstants.QNA_CORE_WS);
		URI uri = restURIProvider.getURI(NLPConstants.HISTORY_DELETE_SERVICE_POSTFIX, String.valueOf(historyId));
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(uri);
	}

	@Override
	public void answerQuestion(UnAnsweredQnA qna) throws URISyntaxException {
		RestURIProvider restURIProvider = new RestURIProvider(NLPConstants.HTTP, NLPConstants.SERVICE_HOST,
				NLPConstants.SERVICE_PORT, NLPConstants.QNA_CORE_WS);
		URI uri = restURIProvider.getURI(NLPConstants.ANSWER_QUESTION_SERVICE_POSTFIX);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(uri, qna);
	}

}
