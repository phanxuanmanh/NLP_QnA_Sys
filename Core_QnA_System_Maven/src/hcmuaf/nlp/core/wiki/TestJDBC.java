package hcmuaf.nlp.core.wiki;

import hcmuaf.nlp.core.dao.WikiConceptDao;
import hcmuaf.nlp.core.jdbcDao.impl.WikiConceptDaoImpl;

public class TestJDBC {
public static void main(String[] args) {
	long start =System.currentTimeMillis();
	WikiConceptDao concepDao = new WikiConceptDaoImpl();
	System.out.println(concepDao.getConceptText(22992321));
	System.out.println(concepDao.getConceptText(22986510));
	long end =System.currentTimeMillis();
	System.out.println("total time spent: "+ (end-start));
}
}
