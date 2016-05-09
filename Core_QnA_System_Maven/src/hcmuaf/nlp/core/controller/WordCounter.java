package hcmuaf.nlp.core.controller;

import hcmuaf.nlp.core.dao.WikiConceptDao;
import hcmuaf.nlp.core.jdbcDao.impl.WikiConceptDaoImpl;
import hcmuaf.nlp.core.model.WordCounterItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class WordCounter {
	private int pageID;
	private ArrayList<WordCounterItem> listWordItem = new ArrayList<WordCounterItem>();
	private Set<Integer> setWid = new HashSet<Integer>();

	public WordCounter(int pageID) {
		super();
		this.pageID = pageID;
	}

	public int getPageID() {
		return pageID;
	}

	public void setPageID(int pageID) {
		this.pageID = pageID;

	}

	public void addWord(int wid) {
		if (setWid.contains(wid)) {
			for (WordCounterItem item : listWordItem) {
				if (item.getWordID() == wid) {
					item.setFreq(item.getFreq() + 1);
				}
			}
		} else {
			setWid.add(wid);
			WordCounterItem item = new WordCounterItem(wid, 1);
			listWordItem.add(item);

		}
	}

	public void updateWordCount() {
		WikiConceptDao conceptDao = new WikiConceptDaoImpl();
		for (WordCounterItem item : listWordItem) {
			// System.out.println("update word:" + item.getWordID() +"  freq:" +
			// item.getFreq());
			conceptDao
					.updateWordCount(pageID, item.getWordID(), item.getFreq());
		}

	}
}
