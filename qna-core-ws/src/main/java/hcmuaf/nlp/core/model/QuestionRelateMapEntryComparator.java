package hcmuaf.nlp.core.model;

import java.util.Comparator;
import java.util.Map.Entry;

public class QuestionRelateMapEntryComparator implements Comparator<Entry<Long, Double>> {

	@Override
	public int compare(Entry<Long, Double> o1, Entry<Long, Double> o2) {
		return o1.getValue().compareTo(o2.getValue());
	}
	

}
