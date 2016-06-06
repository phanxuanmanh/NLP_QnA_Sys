package hcmuaf.nlp.core.test;

import hcmuaf.nlp.core.dao.WikiInvertedIndexDao;
import hcmuaf.nlp.core.model.WikiInvertedIndex;

import java.util.List;

public class Test {
	public static void main(String[] args) {
		WikiInvertedIndexDao jdbcInvertedIndexDao = new hcmuaf.nlp.core.jdbcDao.impl.WikiInvertedIndexDaoImpl();
		List<WikiInvertedIndex> listWikiConceptWordVector = jdbcInvertedIndexDao
				.getAllVector();
		System.out.println(listWikiConceptWordVector.size());
	}
}
