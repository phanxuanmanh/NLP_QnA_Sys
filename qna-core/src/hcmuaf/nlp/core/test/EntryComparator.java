package hcmuaf.nlp.core.test;

import java.util.Comparator;
import java.util.Map.Entry;

public class EntryComparator implements Comparator<Entry<Integer, Double>> {

	@Override
	public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2) {
		return o1.getValue().compareTo(o2.getValue());
	}
	

}
