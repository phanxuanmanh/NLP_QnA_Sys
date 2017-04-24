package hcmuaf.nlp.core.hibernateDao.impl;

import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dao.QuestionTypeDao;
import hcmuaf.nlp.core.model.Question;
import hcmuaf.nlp.core.model.QuestionVectorCsv;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
@Transactional
public class QuestionDaoImpl implements QuestionDao {
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public List<Long> getAvailableQuestionList() {
		Session session =sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Long> listQuestion = session
				.createCriteria(Question.class)
				.add(Restrictions.gt("expireDate", new Date()))
				.setProjection(Projections.distinct(Projections.property("id")))
				.addOrder(Order.asc("id")).list();
		return listQuestion;
	}

	@Override
	public int countQuestion() {
		Session session =sessionFactory.getCurrentSession();
		Long count = (Long) session.createCriteria(Question.class)
				.setProjection(Projections.rowCount()).uniqueResult();
		return count.intValue();
	}

	@Override
	public int getQuestionType(long qId) {
		Session session =sessionFactory.getCurrentSession();
		Object type = session.createCriteria(Question.class)
				.add(Restrictions.eq("id", qId))
				.setProjection(Projections.property("typeId")).uniqueResult();
		if (null == type) {
			return 0;
		} else {
			return Integer.parseInt(type.toString());
		}
	}

	@Override
	public String getQuestionContent(long qId) {
		Session session =sessionFactory.getCurrentSession();
		String content = session.createCriteria(Question.class)
				.add(Restrictions.eq("id", qId))
				.setProjection(Projections.property("content")).uniqueResult()
				.toString();
		return content;
	}
	
	@Override
	public Long insertQuestion(String question, Integer typeID) {
		Session session =sessionFactory.getCurrentSession();
		Question q = new Question();
		q.setContent(question);
		q.setTypeId(typeID);
		session.persist(q);
		return q.getId();
	}

	@Override
	public QuestionVectorCsv readQuestionVectorData(long questionID)
			throws SQLException {
		Session session =sessionFactory.getCurrentSession();
		QuestionTypeDao typeDao = new QuestionTypeDaoImpl();
		ArrayList<String> listWeight = new ArrayList<>();
		Query query = session
				.createSQLQuery("SELECT wid, SUM(weight) AS weight FROM"
						+ " (SELECT kw.wid, 0.0 AS weight FROM  key_words kw 	"
						+ "UNION     SELECT  kw.wid, v.weight FROM  key_words kw, question_vectors v    "
						+ "WHERE        v.q_id = :qid AND kw.wid = v.wid) t GROUP BY wid ORDER BY wid");
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.setLong("qid", questionID).list();
		for (Object[] row : rows) {
			String w = row[0].toString();
			listWeight.add(w);
		}
		int typeID = typeDao.getTypeOfQuestion(questionID);
		QuestionVectorCsv vector = null;
		if (typeID > 0) {
			vector = new QuestionVectorCsv(listWeight, typeID);
		}
		return vector;
	}

	@Override
	public ArrayList<QuestionVectorCsv> readQuestionVectorData()
			throws SQLException {
		List<Long> qlist = getAvailableQuestionList();
		ArrayList<QuestionVectorCsv> data = new ArrayList<>();
		for (long i : qlist) {
			QuestionVectorCsv vector = readQuestionVectorData(i);
			data.add(vector);
		}
		return data;
	}

	@Override
	public void setQuestionType(long questionId, Integer typeID) {
		Session session =sessionFactory.getCurrentSession();
		Question q = (Question) session.get(Question.class, questionId);
		q.setTypeId(typeID);
		session.persist(q);
	}

	@Override
	public Question getQuestionByID(long qId) {
		Session session =sessionFactory.getCurrentSession();
		Question question = (Question) session.get(Question.class, qId);
		return question;
	}

	@Override
	public void updateQuestion(Question question) {
		Session session =sessionFactory.getCurrentSession();
		session.update(question);
		
	}

}
