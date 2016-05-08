package hcmuaf.nlp.core.test;

import hcmuaf.nlp.core.controller.KeyWordFinder;

public class TestWordCount {
	public static void main(String[] args) {
		KeyWordFinder finder = new KeyWordFinder();

		for (int i = 0; i < 3000; i++) {
			finder.questionStatistic(i);
			System.out.println("completed work on question with ID : " + i);
		}

	}
}
