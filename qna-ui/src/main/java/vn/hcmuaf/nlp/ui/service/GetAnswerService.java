package vn.hcmuaf.nlp.ui.service;

import java.util.ArrayList;
import java.util.List;

import demo.DemoClass;
import vn.hcmuaf.nlp.ui.model.QnAPairModel;
import hcmuaf.nlp.core.dto.QnAPair;
import hcmuaf.nlp.core.model.Answer;
import hcmuaf.nlp.core.service.QnAProcessService;
import hcmuaf.nlp.core.service.impl.QnAProcessServiceImpl;

public class GetAnswerService {
	//public static QnAProcessService qnaService = new QnAProcessServiceImpl();

	public static List<QnAPairModel> getListRelateQuestion(String question) {
		/*List<QnAPairModel> listReturnQnA = new ArrayList<QnAPairModel>();
		List<QnAPair> listQnA = qnaService
				.getListRelateQnA(question);
		for (QnAPair pair : listQnA) {
			QnAPairModel returnPair = new QnAPairModel();
			
			returnPair.setAnswerId(pair.getAnswerId());
			returnPair.setQuestionId(pair.getQuestionId());
			returnPair.setQuestionContent(pair.getQuestion());
			returnPair.setAnswerContent(pair.getAnswer());
			returnPair.setTypeId(pair.getTypeID());
			listReturnQnA.add(returnPair);
		}*/
		DemoClass c = new DemoClass();
		return null;
	}
}
