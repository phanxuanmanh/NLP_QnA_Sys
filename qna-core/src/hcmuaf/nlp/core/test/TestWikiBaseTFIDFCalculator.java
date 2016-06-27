package hcmuaf.nlp.core.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import hcmuaf.nlp.core.wiki.WikiBaseTFIDFCalculator;

public class TestWikiBaseTFIDFCalculator {
	public static void main(String[] args) {
		WikiBaseTFIDFCalculator calculator = new WikiBaseTFIDFCalculator();
		//calculator.calculateTFIDF();
		HashMap<Integer, Double> interpretationVertor = calculator
				.buildInterpretationVertor(1);
		List<Entry<Integer, Double>> list = new ArrayList<Entry<Integer, Double>>(
				interpretationVertor.entrySet());
		Collections.sort(list, new EntryComparator());
		if(list.size()> 11){
		for(int i =list.size() -1;i>(list.size()-10); i-- ){
			Entry<Integer, Double> entry = list.get(i);
			System.out.println("concept : " + entry.getKey() +" ,value : "+ entry.getValue());
		}
		}
	}
}
