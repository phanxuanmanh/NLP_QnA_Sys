package vn.hcmuaf.nlp.util;

import hcmuaf.nlp.core.dto.QnAPair;
import hcmuaf.nlp.core.service.QnAProcessService;
import hcmuaf.nlp.core.service.impl.QnAProcessServiceImpl;

import java.util.ArrayList;
import java.util.List;

import vn.hcmuaf.nlp.ui.model.QnAPairModel;

public class Test {
	public static QnAProcessService qnaService = new QnAProcessServiceImpl();
	public static void main(String[] args) {
		List<QnAPairModel> listReturnQnA = getListRelateQuestion("em chao thay");
		for(QnAPairModel q: listReturnQnA){
			System.out.println(q.getQuestionContent());
		}
	}
	public static List<QnAPairModel> getListRelateQuestion(String question) {
		List<QnAPairModel> listReturnQnA = new ArrayList<QnAPairModel>();
		List<QnAPair> listQnA = qnaService.getListRelateQnA(question);
		for (QnAPair pair : listQnA) {
			QnAPairModel returnPair = new QnAPairModel();
			
			returnPair.setAnswerId(pair.getAnswerId());
			returnPair.setQuestionId(pair.getQuestionId());
			returnPair.setQuestionContent(pair.getQuestion());
			returnPair.setAnswerContent(pair.getAnswer());
			returnPair.setTypeId(pair.getTypeID());
			listReturnQnA.add(returnPair);
		}
		return listReturnQnA;
	}
}
