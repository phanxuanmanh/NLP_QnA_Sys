package hcmuaf.nlp.core.wiki;

import hcmuaf.nlp.core.dao.AnswerDao;
import hcmuaf.nlp.core.hibernateDao.impl.AnswerDaoImpl;

public class TestAddAnswer {

	public static void main(String[] args) {
		AnswerDao answerDao = new AnswerDaoImpl();
		System.out.println("New insert ID: "+ answerDao.insertAnswer("Demo"));

	}

}
