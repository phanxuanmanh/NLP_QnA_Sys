package hcmuaf.nlp.core.runnable;

import java.util.Comparator;
import java.util.Map.Entry;

public class EntryComparator2 implements Comparator<Entry<Integer, Integer>> {

	@Override
	public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
		return o1.getKey().compareTo(o2.getKey());
	}
	

}
