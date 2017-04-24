package hcmuaf.nlp.core.hibernateDao.impl;

import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dao.QuestionTypeDao;
import hcmuaf.nlp.core.model.QuestionType;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class QuestionTypeDaoImpl implements QuestionTypeDao {
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	private QuestionDao questionDao;
	
	public QuestionTypeDaoImpl() {
	}
	
	@Override
	public ArrayList<QuestionType> getQuestionTypeList() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from QuestionType");
		@SuppressWarnings("unchecked")
		List<QuestionType> listType = query.list();
		return (ArrayList<QuestionType>) listType;
	}
	
	@Override
	public int getTypeOfQuestion(long qId) {
		return questionDao.getQuestionType(qId);
	}
}
