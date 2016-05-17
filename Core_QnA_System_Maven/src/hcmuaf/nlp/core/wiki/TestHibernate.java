package hcmuaf.nlp.core.wiki;

import hcmuaf.nlp.core.dao.QuestionVectorDao;
import hcmuaf.nlp.core.hibernateDao.impl.QuestionVectorDaoImpl;

public class TestHibernate {
	public static void main(String[] args) {
		QuestionVectorDao vectorDao = new QuestionVectorDaoImpl();
		vectorDao.updateWordCount(1, 9954);
	}
}
