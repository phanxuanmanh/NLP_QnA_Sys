package hcmuaf.nlp.core.wiki;

import hcmuaf.nlp.core.model.WordCounterItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Test {
	public static void main(String[] args) {
		ArrayList<WordCounterItem> listWordItem = new ArrayList<WordCounterItem>();
		listWordItem.add(new WordCounterItem(1, 2));
		listWordItem.add(new WordCounterItem(2, 0));
		listWordItem.add(new WordCounterItem(3, 2));
		for (WordCounterItem item : listWordItem) {
			if (item.getWordID() == 2) {
				item.setFreq(item.getFreq() + 2);
			}
		}
		for (WordCounterItem item : listWordItem) {

			System.out.println("word ID : " + item.getWordID() + " freq: "
					+ item.getFreq());

		}

	}
}
