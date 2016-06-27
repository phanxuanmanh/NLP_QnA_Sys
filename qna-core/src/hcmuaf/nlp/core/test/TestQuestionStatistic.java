package hcmuaf.nlp.core.test;

import java.util.List;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.hibernateDao.impl.KeyWordDaoImpl;
import hcmuaf.nlp.core.hibernateDao.impl.QuestionDaoImpl;
import hcmuaf.nlp.core.processing.KeyWordFinder;

public class TestQuestionStatistic {
	public static void main(String[] args) {
		QuestionDao questionDao = new QuestionDaoImpl();
		KeyWordFinder finder = new KeyWordFinder();
		finder.setKeyWordDao(new KeyWordDaoImpl());
		finder.setTagger(new VietnameseMaxentTagger());
		finder.setQuestionDao(questionDao);
		List<Integer> listQuestion = questionDao.getQuestionList();
		for(Integer questionId : listQuestion){
			finder.questionStatistic(questionId.intValue());
		}
	}
}
