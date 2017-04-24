package vn.hcmuaf.nlp.ui.service;

import java.net.URISyntaxException;
import java.util.List;

import vn.hcmuaf.nlp.ui.model.CommonQuestionSearchCriteria;
import vn.hcmuaf.nlp.ui.model.QnAPair;
import vn.hcmuaf.nlp.ui.model.QuestionHistory;
import vn.hcmuaf.nlp.ui.service.impl.QnAServiceImpl;

public class QnAServiceProvider {
	private static QnAService qnaService = new QnAServiceImpl();
	
	public static List<QnAPair> getListRelateQuestion(String question) throws URISyntaxException {
		return qnaService.getRelateQnA(question);
	}
	
	public static List<QnAPair> getCommonQnA(CommonQuestionSearchCriteria searchCriteria) {
		try {
			return qnaService.getCommonQnA(searchCriteria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void addQuestionHistory(QuestionHistory questionHistory) {
		try {
			qnaService.inserQuestionHistory(questionHistory);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public static List<QnAPair> getQnAHistoryByUser(int userId) {
		try {
			return qnaService.getQnAHistoryByUser(userId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
