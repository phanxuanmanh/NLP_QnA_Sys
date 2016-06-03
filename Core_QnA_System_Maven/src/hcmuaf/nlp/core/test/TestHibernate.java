package hcmuaf.nlp.core.test;

import hcmuaf.nlp.core.dao.WikiConceptVectorDao;
import hcmuaf.nlp.core.hibernateDao.impl.WikiConceptVectorDaoImpl;


public class TestHibernate {
	public static void main(String[] args) {
		//System.out.println((double)Math.log10(2000000 / 11) * ((double)1/40));
		WikiConceptVectorDao vectorDao = new WikiConceptVectorDaoImpl();
		System.out.println(vectorDao.getWordWithFreq().get(6580));
	}
}
