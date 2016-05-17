package hcmuaf.nlp.core.hibernateDao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dao.QuestionTypeDao;
import hcmuaf.nlp.core.model.QuestionType;
import hcmuaf.nlp.core.util.HibernateUtil;

public class QuestionTypeDaoImpl implements QuestionTypeDao {
	SessionFactory sessionFactory;
	Session session;

	public QuestionTypeDaoImpl() {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
	}

	@Override
	public ArrayList<QuestionType> getTypeList() {
		Query query = session.createQuery("from QuestionType");
		@SuppressWarnings("unchecked")
		List<QuestionType> listType = query.list();
		return (ArrayList<QuestionType>) listType;
	}

	@Override
	public int getQuestionType(int qId) {
		QuestionDao questionDao = new QuestionDaoImpl();
		return questionDao.getQuestionType(qId);
	}

}
