package hcmuaf.nlp.core.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.hibernateDao.impl.QuestionDaoImpl;
import hcmuaf.nlp.core.wiki.QuestionComparator;

public class TestQuestionComparator {
	public static void main(String[] args) {
		QuestionComparator comparator = new QuestionComparator();
		HashMap<Integer, Double> relationList = comparator.getRelationList(10);
		List<Entry<Integer, Double>> list = new ArrayList<Entry<Integer, Double>>(
				relationList.entrySet());
		QuestionDao questionDao = new QuestionDaoImpl();
		Collections.sort(list, new EntryComparator());
		if (list.size() > 11) {
			for (int i = list.size() - 1; i > (list.size() - 10); i--) {
				Entry<Integer, Double> entry = list.get(i);
				System.out.println("question content : "
						+ questionDao.getQuestion(entry.getKey())
						+ " ,value : " + entry.getValue().doubleValue());

			}
		}

	}
}
