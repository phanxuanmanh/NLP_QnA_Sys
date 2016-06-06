package hcmuaf.nlp.core.hibernateDao.impl;

import hcmuaf.nlp.core.dao.KeyWordDao;
import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dao.QuestionVectorDao;
import hcmuaf.nlp.core.model.QuestionVector;
import hcmuaf.nlp.core.model.QuestionVectorCsv;
import hcmuaf.nlp.core.model.QuestionWordPair;
import hcmuaf.nlp.core.util.HibernateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
		session = sessionFactory.getCurrentSession();
		ArrayList<QuestionVector> listVector = new ArrayList<>();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createSQLQuery("SELECT q_id, wid, weight, freq FROM question_vectors where q_id =:qid");
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.setInteger("qid", questionID).list();
		for (Object[] row : rows) {
			int qid = Integer.parseInt(row[0].toString());
			int wordID = Integer.parseInt(row[1].toString());
			double weight = Double.parseDouble(row[2].toString());
			int frq = Integer.parseInt(row[3].toString());
			QuestionVector vector = new QuestionVector(qid, wordID, frq, weight);
			listVector.add(vector);

		}
		tx.commit();
		return listVector;
	}

	@Override
	public void updateWordCount(int questionId, int wordId) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		QuestionVector vector = (QuestionVector) session.get(
				QuestionVector.class, new QuestionWordPair(questionId, wordId));
		vector.setFreq(vector.getFreq() + 1);
		session.update(vector);
		tx.commit();
	}

	@Override
	public int numOfQuestionContainWord(int wid) {
		KeyWordDao keyWordDao = new KeyWordDaoImpl();
		return keyWordDao.numOfQuestionContainWord(wid);
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

}
