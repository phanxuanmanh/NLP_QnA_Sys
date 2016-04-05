package hcmuaf.nlp.core.test;

import java.util.ArrayList;
import java.util.List;

import hcmuaf.nlp.core.DBConnect.WordAccessor;
import hcmuaf.nlp.core.controller.KeyWordCalculator;
import hcmuaf.nlp.core.model.Keyword;

public class TestUpdateTFIDF {
	public static void main(String[] args) {
		KeyWordCalculator cal = new KeyWordCalculator();
		/*List<Keyword> listKW = WordAccessor.getListkeyWord2();
		for (Keyword k : listKW) {
			System.out.println(k.getId());
			cal.updateIDF(k.getId());
		}*/
		for(int i=0; i<3000; i++){
			cal.updateTFIDF(i);
			System.out.println(i);
		}
	}
}
