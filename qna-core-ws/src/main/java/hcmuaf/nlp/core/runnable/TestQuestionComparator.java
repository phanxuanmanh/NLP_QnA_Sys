package hcmuaf.nlp.core.runnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.hibernateDao.impl.QuestionDaoImpl;
import hcmuaf.nlp.core.model.QuestionRelateMapEntryComparator;
import hcmuaf.nlp.core.service.QuestionComparator;
import hcmuaf.nlp.core.service.impl.QuestionComparatorImpl;

public class TestQuestionComparator {
	public static void main(String[] args) {
		QuestionComparator comparator = new QuestionComparatorImpl();
		HashMap<Long, Double> relationList = comparator.getRelationList(10);
		List<Entry<Long, Double>> list = new ArrayList<Entry<Long, Double>>(
				relationList.entrySet());
		QuestionDao questionDao = new QuestionDaoImpl();
		Collections.sort(list, new QuestionRelateMapEntryComparator());
		if (list.size() > 11) {
			for (int i = list.size() - 1; i > (list.size() - 10); i--) {
				Entry<Long, Double> entry = list.get(i);
				System.out.println("question content : "
						+ questionDao.getQuestionContent(entry.getKey())
						+ " ,value : " + entry.getValue().doubleValue());

			}
		}

	}
}
