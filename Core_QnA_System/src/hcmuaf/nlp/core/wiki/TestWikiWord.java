package hcmuaf.nlp.core.wiki;

import hcmuaf.nlp.core.DBConnect.WikiContentAccessor;
import hcmuaf.nlp.core.DBConnect.WordAccessor;
import hcmuaf.nlp.core.controller.WikiWordFinder;

import java.util.ArrayList;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;

public class TestWikiWord {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		WikiWordFinder finder = new WikiWordFinder(new WordAccessor(),
				new VietnameseMaxentTagger());
		ArrayList<Integer> listPage = WikiContentAccessor.getConCeptList();
		for (int i = 5; i < 30; i++)
			finder.conceptStatistic(listPage.get(i));

		long end = System.currentTimeMillis();
		System.out.println("time spent in ms : " + (end - start));
	}
}
