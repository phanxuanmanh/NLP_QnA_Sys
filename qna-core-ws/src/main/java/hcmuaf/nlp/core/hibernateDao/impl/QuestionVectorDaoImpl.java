package hcmuaf.nlp.core.hibernateDao.impl;

import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dao.QuestionVectorDao;
import hcmuaf.nlp.core.model.QuestionVector;
import hcmuaf.nlp.core.model.QuestionVectorCsv;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
public class QuestionVectorDaoImpl implements QuestionVectorDao {
	@Autowired
	SessionFactory sessionFactory;
	
	public QuestionVectorDaoImpl() {
	}
	
	@Override
	public void updateTFIDF(long qid, long wid, double tfidf) {
		Session session = sessionFactory.getCurrentSession();
		QuestionVector vector = new QuestionVector();
		vector.setWordID(wid);
		vector.setQuestionID(qid);
		vector.setTfidf(tfidf);
		session.persist(vector);
	}
	
	@Override
	public ArrayList<QuestionVector> getListVector(long questionID) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<QuestionVector> listVector = session.createCriteria(QuestionVector.class)
				.add(Restrictions.eq("questionID", questionID)).addOrder(Order.asc("wordID")).list();
		return new ArrayList<QuestionVector>(listVector);
	}
	
	@Override
	public ArrayList<QuestionVectorCsv> readQuestionVectorData() throws SQLException {
		QuestionDao qDao = new QuestionDaoImpl();
		return qDao.readQuestionVectorData();
	}
	
	@Override
	public QuestionVectorCsv readQuestionVectorData(long questionID) throws SQLException {
		QuestionDao qDao = new QuestionDaoImpl();
		return qDao.readQuestionVectorData(questionID);
	}
	
	@Override
	@Transactional
	public void saveWordCount(Map<Long, Long> listWordWithFreq, long questionId) {
		Session session = sessionFactory.getCurrentSession();
		listWordWithFreq.entrySet().stream().forEach(entry -> {
			QuestionVector vector = new QuestionVector();
			vector.setFreq(entry.getValue().intValue());
			vector.setWordID(entry.getKey().longValue());
			vector.setQuestionID(questionId);
			vector.setTfidf(new Double(0d));
			session.save(vector);
		});
	}
	
	@Override
	public Map<Long, Integer> getWordWithFreq() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select wid, count(q_id) from question_vectors  group by wid");
		@SuppressWarnings("unchecked")
		List<Object[]> result = query.list();
		Map<Long, Integer> listWordFreq = result.stream().collect(
				Collectors.toMap(row -> Long.valueOf(row[0].toString()), row -> Integer.valueOf(row[1].toString())));
		return new HashMap<Long, Integer>(listWordFreq);
	}
	
	@Override
	public void updateListVector(List<QuestionVector> listWordVectors) {
		Session session = sessionFactory.getCurrentSession();
		listWordVectors.forEach(vector -> session.update(vector));
	}
	
	@Override
	public List<Long> getListKeyWord() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Long> listWordId = session.createCriteria(QuestionVector.class)
				.setProjection(Projections.distinct(Projections.property("wordID"))).addOrder(Order.asc("wordID"))
				.list();
		return listWordId;
	}
	
	@Override
	public List<QuestionVector> getAllVector() {
		long numberConcept = currentNumberOfVector();
		List<QuestionVector> listAllCOncept = new ArrayList<QuestionVector>();
		Session session = sessionFactory.getCurrentSession();
		for (int i = 1; i < numberConcept; i = i + 50000) {
			@SuppressWarnings("unchecked")
			List<QuestionVector> listConcept = session.createCriteria(QuestionVector.class).setFirstResult(i)
					.setMaxResults(50000).list();
			listAllCOncept.addAll(listConcept);
		}
		return listAllCOncept;
	}
	
	@Override
	public Long currentNumberOfVector() {
		Session session = sessionFactory.getCurrentSession();
		Long wordCount = (Long) session.createCriteria(QuestionVector.class).setProjection(Projections.count("id"))
				.uniqueResult();
		return wordCount;
	}
}
