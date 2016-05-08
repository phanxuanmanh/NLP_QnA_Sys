package hcmuaf.nlp.core.test;

import hcmuaf.nlp.core.controller.KeyWordCalculator;

public class TestUpdateTFIDF {
	public static void main(String[] args) {
		KeyWordCalculator cal = new KeyWordCalculator();
		/*List<Keyword> listKW = WordAccessor.getListkeyWord2();
		for (Keyword k : listKW) {
			System.out.println(k.getId());
			cal.updateIDF(k.getId());
		}*/
		//update TFIDF for question
		for(int i=0; i<3000; i++){
			cal.updateTFIDF(i);
			System.out.println(i);
		}
	}
}
