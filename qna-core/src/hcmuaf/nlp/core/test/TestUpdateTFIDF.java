package hcmuaf.nlp.core.test;

import hcmuaf.nlp.core.processing.KeyWordCalculator;

public class TestUpdateTFIDF {
	public static void main(String[] args) {
		KeyWordCalculator cal = new KeyWordCalculator();
		cal.calculateTFIDF();
	}
}
