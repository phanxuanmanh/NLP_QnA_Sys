package hcmuaf.nlp.core.hibernateDao.impl;

import hcmuaf.nlp.core.dao.KeyWordDao;
import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dao.QuestionTypeDao;
import hcmuaf.nlp.core.dao.QuestionVectorDao;
import hcmuaf.nlp.core.model.Question;
import hcmuaf.nlp.core.model.QuestionVectorCsv;
import hcmuaf.nlp.core.util.HibernateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class QuestionDaoImpl implements QuestionDao {
	SessionFactory sessionFactory;
	Session session;

	@Override
	public ArrayList<Integer> getQuestionList() {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		ArrayList<Integer> listQuestion = new ArrayList<Integer>();
		Transaction tx = session.beginTransaction();
		Query query = session.createSQLQuery("select q_id from questions");
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.list();
		for (Object[] row : rows) {
			Integer questionID = new Integer(row[0].toString());
			listQuestion.add(questionID);

		}
		tx.commit();
		session.close();
		return listQuestion;
	}

	@Override
	public int countQuestion() {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Long count = (Long) session.createCriteria(Question.class)
				.setProjection(Projections.rowCount()).uniqueResult();
		tx.commit();
		session.close();
		return count.intValue();
	}

	@Override
	public int getQuestionType(int qId) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int typeID = Integer.parseInt(session.createCriteria(Question.class)
				.add(Restrictions.eq("id", qId))
				.setProjection(Projections.property("typeId")).uniqueResult()
				.toString());
		tx.commit();
		session.close();
		return typeID;
	}

	@Override
	public String getQuestion(int qId) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String content = session.createCriteria(Question.class)
				.add(Restrictions.eq("id", qId))
				.setProjection(Projections.property("content")).uniqueResult()
				.toString();
		tx.commit();
		session.close();
		return content;
	}

	@Override
	public int insertQuestion(String question, int typeID) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Question q = new Question();
		q.setContent(question);
		q.setTypeId(typeID);
		session.persist(q);
		tx.commit();
		session.close();
		return q.getId();

	}

	@Override
	public QuestionVectorCsv readQuestionVectorData(int questionID)
			throws SQLException {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		QuestionTypeDao typeDao = new QuestionTypeDaoImpl();
		ArrayList<String> listWeight = new ArrayList<>();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createSQLQuery("SELECT wid, SUM(weight) AS weight FROM"
						+ " (SELECT kw.wid, 0.0 AS weight FROM  key_words kw 	"
						+ "UNION     SELECT  kw.wid, v.weight FROM  key_words kw, question_vectors v    "
						+ "WHERE        v.q_id = :qid AND kw.wid = v.wid) t GROUP BY wid ORDER BY wid");
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.setInteger("qid", questionID).list();
		for (Object[] row : rows) {
			String w = row[0].toString();
			listWeight.add(w);
		}
		int typeID = typeDao.getQuestionType(questionID);
		QuestionVectorCsv vector = null;
		if (typeID > 0) {
			vector = new QuestionVectorCsv(listWeight, typeID);
		}
		tx.commit();
		session.close();
		return vector;
	}

	@Override
	public ArrayList<QuestionVectorCsv> readQuestionVectorData()
			throws SQLException {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		ArrayList<Integer> qlist = getQuestionList();
		ArrayList<QuestionVectorCsv> data = new ArrayList<>();
		Transaction tx = session.beginTransaction();
		for (int i : qlist) {
			QuestionVectorCsv vector = readQuestionVectorData(i);
			data.add(vector);
		}
		tx.commit();
		session.close();
		return data;
	}

	@Override
	public void updateWordCount(int questionId, int wordId) {
		QuestionVectorDao vectorDao = new QuestionVectorDaoImpl();
		vectorDao.updateWordCount(questionId, wordId);

	}

	@Override
	public int numOfQuestionContainWord(int wid) {
		KeyWordDao keyWordDao = new KeyWordDaoImpl();
		return keyWordDao.numOfQuestionContainWord(wid);
	}

}
