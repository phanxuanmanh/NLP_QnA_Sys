package vn.hcmuaf.nlp.ui.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import vn.hcmuaf.nlp.ui.model.CommonQuestionSearchCriteria;
import vn.hcmuaf.nlp.ui.model.QnAPair;
import vn.hcmuaf.nlp.ui.model.QuestionHistory;
import vn.hcmuaf.nlp.ui.service.QnAService;
import vn.hcmuaf.nlp.util.NLPConstants;
import vn.hcmuaf.nlp.util.RestURIProvider;

public class QnAServiceImpl implements QnAService {
	@Override
	public List<QnAPair> getRelateQnA(String question) throws URISyntaxException {
		RestURIProvider restURIProvider = new RestURIProvider(NLPConstants.HTTP, NLPConstants.SERVICE_HOST,
				NLPConstants.SERVICE_PORT, NLPConstants.QNA_CORE_WS);
		URI uri = restURIProvider.getURI(NLPConstants.QNA_SERVICE_PREFIX, "");
		RestTemplate restTemplat = new RestTemplate();
		restTemplat.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		QnAPair[] qnas = restTemplat.postForObject(uri, question, QnAPair[].class);
		return new ArrayList<QnAPair>(Arrays.asList(qnas));
	}
	
	@Override
	public List<QnAPair> getQnA(int questionId) {
		return null;
	}
	
	@Override
	public Integer updateQnA(QnAPair qnaPair) {
		return null;
	}
	
	@Override
	public void inserQuestionHistory(QuestionHistory history) throws URISyntaxException {
		RestURIProvider restURIProvider = new RestURIProvider(NLPConstants.HTTP, NLPConstants.SERVICE_HOST,
				NLPConstants.SERVICE_PORT, NLPConstants.QNA_CORE_WS);
		URI uri = restURIProvider.getURI(NLPConstants.HISTORY_ADD_SERVICE_POSTFIX);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(uri, history);
	}
	
	@Override
	public List<QnAPair> getCommonQnA(CommonQuestionSearchCriteria searchCriteria) throws Exception {
		RestURIProvider restURIProvider =
				new RestURIProvider(NLPConstants.HTTP, NLPConstants.SERVICE_HOST,
				NLPConstants.SERVICE_PORT, NLPConstants.QNA_CORE_WS);
		URI uri = restURIProvider.getURI(NLPConstants.COMMON_QUESTION_SERVICE_POSTFIX);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<QnAPair[]> responseEntity = 
				restTemplate.postForEntity(uri, searchCriteria, QnAPair[].class);
		List<QnAPair> responseList = new ArrayList<QnAPair>(Arrays.asList(responseEntity.getBody()));
		return responseList;
	}
	
	@Override
	public List<QnAPair> getQnAHistoryByUser(int userId) throws Exception {
		RestURIProvider restURIProvider = new RestURIProvider(NLPConstants.HTTP, NLPConstants.SERVICE_HOST,
				NLPConstants.SERVICE_PORT, NLPConstants.QNA_CORE_WS);
		URI uri = restURIProvider.getURI(NLPConstants.HISTORY_GET_BY_USER_SERVICE_POSTFIX, String.valueOf(userId));
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		ResponseEntity<QnAPair[]> entity = restTemplate.getForEntity(uri, QnAPair[].class);
		return new ArrayList<QnAPair>(Arrays.asList(entity.getBody()));
	}
}
