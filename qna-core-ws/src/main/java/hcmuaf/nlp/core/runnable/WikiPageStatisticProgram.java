package hcmuaf.nlp.core.runnable;

import hcmuaf.nlp.core.dao.KeyWordDao;
import hcmuaf.nlp.core.dao.WikiConceptDao;
import hcmuaf.nlp.core.hibernateDao.impl.WikiConceptDaoImpl;
import hcmuaf.nlp.core.util.KeyWordDaoGenerator;
import hcmuaf.nlp.core.wiki.WikiWordFinder;

import java.util.ArrayList;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;

public class WikiPageStatisticProgram {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		WikiConceptDao conceptDao = new WikiConceptDaoImpl();
		VietnameseMaxentTagger tagger = new VietnameseMaxentTagger();
		KeyWordDao keyWordDao = KeyWordDaoGenerator.getCurrentKeyWordDao();
		WikiWordFinder finder = new WikiWordFinder();
		finder.setKeyWordDao(keyWordDao);
		finder.setTagger(tagger);
		ArrayList<Integer> listPage = conceptDao.getConceptList();
		for (int i = 1; i < listPage.size(); i++)
			finder.conceptStatistic(listPage.get(i));
		long end = System.currentTimeMillis();
		System.out.println("time spent in ms : " + (end - start));
	}
}
