package hcmuaf.nlp.core.runnable;

import hcmuaf.nlp.core.service.QuestionTFIDFService;
import hcmuaf.nlp.core.service.impl.QuestionTFIDFPServiceImpl;

public class TestUpdateTFIDF {
	public static void main(String[] args) {
		QuestionTFIDFService cal = new QuestionTFIDFPServiceImpl();
		cal.calculateTFIDF();
	}
}
