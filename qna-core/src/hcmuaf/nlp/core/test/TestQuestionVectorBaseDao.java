package hcmuaf.nlp.core.test;

import java.util.List;

import hcmuaf.nlp.core.dao.QuestionVectorBaseWikiDao;
import hcmuaf.nlp.core.hibernateDao.impl.QuestionVectorBaseWikiDaoImpl;
import hcmuaf.nlp.core.model.QuestionVectorBaseWiki;

public class TestQuestionVectorBaseDao {
public static void main(String[] args) {
	QuestionVectorBaseWikiDao vectorDao = new QuestionVectorBaseWikiDaoImpl();
	List<QuestionVectorBaseWiki> listvector = vectorDao.getListVector(2);
	System.out.println(listvector.size());
	for(QuestionVectorBaseWiki vector: listvector){
		System.out.println(" word id: "+ vector.getWordID() + " freq: "+ vector.getFreq());
	}
}
}
