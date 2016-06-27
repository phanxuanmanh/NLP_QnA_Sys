package hcmuaf.nlp.core.hibernateDao.impl;

import hcmuaf.nlp.core.dao.QuestionVectorBaseWikiDao;
import hcmuaf.nlp.core.model.QuestionVectorBaseWiki;
import hcmuaf.nlp.core.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class QuestionVectorBaseWikiDaoImpl implements QuestionVectorBaseWikiDao {
	SessionFactory sessionFactory;
	Session session;

	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionVectorBaseWiki> getListVector() {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		List<QuestionVectorBaseWiki> listVector = new ArrayList<>();
		Transaction tx = session.beginTransaction();
		listVector = session.createCriteria(QuestionVectorBaseWiki.class)
				.list();
		tx.commit();
		session.close();
		return listVector;
	}

	@Override
	public void updateQuestionVector(QuestionVectorBaseWiki vector) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(vector);
		tx.commit();
		session.close();
	}

	@Override
	public void updateListQuestionVector(List<QuestionVectorBaseWiki> listVector) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		for (QuestionVectorBaseWiki vector : listVector) {
			session.update(vector);
		}
		tx.commit();
		session.close();
	}

	@Override
	public List<QuestionVectorBaseWiki> getListVector(int questionId) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<QuestionVectorBaseWiki> listVector = session
				.createCriteria(QuestionVectorBaseWiki.class)
				.add(Restrictions.eq("questionID", questionId)).list();
		tx.commit();
		session.close();
		return listVector;
	}

	@Override
	public List<Integer> getListQuestionId() {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Integer> listCOncept = session
				.createCriteria(QuestionVectorBaseWiki.class)
				.setProjection(
						Projections.distinct(Projections.property("questionID")))
				.addOrder(Order.asc("questionID")).list();
		tx.commit();
		session.close();
		return listCOncept;
	}

	@Override
	public List<Integer> getListWordId() {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Integer> listWordIds = session
				.createCriteria(QuestionVectorBaseWiki.class)
				.setProjection(
						Projections.distinct(Projections.property("wordID")))
				.addOrder(Order.asc("wordID")).list();
		tx.commit();
		session.close();
		return listWordIds;
	}

}
