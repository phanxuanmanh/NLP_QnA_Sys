package hcmuaf.nlp.core.hibernateDao.impl;

import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dao.QuestionVectorDao;
import hcmuaf.nlp.core.model.QuestionVector;
import hcmuaf.nlp.core.model.QuestionVectorCsv;
import hcmuaf.nlp.core.util.HibernateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class QuestionVectorDaoImpl implements QuestionVectorDao {
	SessionFactory sessionFactory;
	Session session;

	public QuestionVectorDaoImpl() {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
	}

	@Override
	public void updateTFIDF(int qid, int wid, double tfidf) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		QuestionVector vector = new QuestionVector();
		vector.setWordID(wid);
		vector.setQuestionID(qid);
		vector.setTfidf(tfidf);
		session.persist(vector);
		tx.commit();
	}

	@Override
	public ArrayList<QuestionVector> getListVector(int questionID) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<QuestionVector> listVector = session
				.createCriteria(QuestionVector.class)
				.add(Restrictions.eq("questionID", questionID)).addOrder(Order.asc("wordID")).list();
		tx.commit();
		session.close();
		return new ArrayList<QuestionVector>(listVector);
	}


	
	@Override
	public ArrayList<QuestionVectorCsv> readQuestionVectorData()
			throws SQLException {
		QuestionDao qDao = new QuestionDaoImpl();
		return qDao.readQuestionVectorData();
	}

	@Override
	public QuestionVectorCsv readQuestionVectorData(int questionID)
			throws SQLException {
		QuestionDao qDao = new QuestionDaoImpl();
		return qDao.readQuestionVectorData(questionID);
	}

	@Override
	public void saveWordCount(HashMap<Integer, Integer> listWordWithFreq,
			int questionId) {
		List<Entry<Integer, Integer>> listEntry = new ArrayList<Entry<Integer, Integer>>(
				listWordWithFreq.entrySet());
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		for (Entry<Integer, Integer> entry : listEntry) {
			QuestionVector vector = new QuestionVector();
			vector.setFreq(entry.getValue().intValue());
			vector.setWordID(entry.getKey().intValue());
			vector.setQuestionID(questionId);
			session.persist(vector);
		}
		tx.commit();
		session.close();
	}

	@Override
	public HashMap<Integer, Integer> getWordWithFreq() {
		HashMap<Integer, Integer> listWordFreq = new HashMap<Integer, Integer>();
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createSQLQuery("select wid, count(q_id) from question_vectors  group by wid");
		@SuppressWarnings("unchecked")
		List<Object[]> result = query.list();
		for (Object[] row : result) {
			Integer wordId = Integer.valueOf(row[0].toString());
			Integer freq = Integer.valueOf(row[1].toString());
			listWordFreq.put(wordId, freq);
		}
		tx.commit();
		System.out.println("load words with frequence done");
		session.close();
		return listWordFreq;
	}

	@Override
	public void updateListVector(List<QuestionVector> listWordVectors) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		for (QuestionVector vector : listWordVectors) {
			session.update(vector);
		}
		tx.commit();
		session.close();
		
	}

	@Override
	public List<Integer> getListKeyWord() {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Integer> listWordId = session
				.createCriteria(QuestionVector.class)
				.setProjection(
						Projections.distinct(Projections.property("wordID")))
				.addOrder(Order.asc("wordID")).list();
		tx.commit();
		session.close();
		return listWordId;
	}
}
