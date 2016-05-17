package hcmuaf.nlp.core.wiki;
import hcmuaf.nlp.core.controller.WikiWordFinder;
import hcmuaf.nlp.core.dao.WikiConceptDao;
import hcmuaf.nlp.core.hibernateDao.impl.WikiConceptDaoImpl;

import java.util.ArrayList;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;

public class TestWikiWord {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		WikiConceptDao conceptDao = new WikiConceptDaoImpl();
		WikiWordFinder finder = new WikiWordFinder(
				new VietnameseMaxentTagger());
		ArrayList<Integer> listPage = conceptDao.getConCeptList();
		for (int i = 5; i < 10; i++)
			finder.conceptStatistic(listPage.get(i));

		long end = System.currentTimeMillis();
		System.out.println("time spent in ms : " + (end - start));
	}
}
