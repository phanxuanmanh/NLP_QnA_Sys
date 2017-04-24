package hcmuaf.nlp.core.hibernateDao.impl;

import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dao.QuestionHistoryDao;
import hcmuaf.nlp.core.model.Question;
import hcmuaf.nlp.core.model.QuestionHistory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class QuestionHistoryDaoImpl implements QuestionHistoryDao {
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	QuestionDao questionDao;
	@Override
	public QuestionHistory getQuestionHistoryById(Long historyId) {
		Session session = sessionFactory.getCurrentSession();
		QuestionHistory questionHistory = (QuestionHistory) session.get(QuestionHistory.class, historyId);
		return questionHistory;
	}
	
	@Override
	public QuestionHistory getQuestionHistoryRootQuestion(Long questionId) {
		Session session = sessionFactory.getCurrentSession();
		QuestionHistory questionHistory = (QuestionHistory) session.createCriteria(QuestionHistory.class)
				.add(Restrictions.eq("questionId", questionId)).uniqueResult();
		return questionHistory;
	}
	
	@Override
	public List<QuestionHistory> getQuestionHistoryRootReferQuestion(Long questionId) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<QuestionHistory> questionHistories = session.createCriteria(QuestionHistory.class)
				.add(Restrictions.eq("ref_q_id", questionId)).list();
		return questionHistories;
	}
	
	@Override
	public void insertQuestionHistory(QuestionHistory questionHistory) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(questionHistory);
	}
	
	@Override
	public List<Question> getRecentQuestions(Date from) {
		Session session = sessionFactory.getCurrentSession();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String dateStr = dateFormat.format(from);
		Query query = session.createSQLQuery("SELECT ref_q_id, COUNT(q_id)" + " FROM question_history"
				+ " where ref_q_id is not null " + "and created_date > STR_TO_DATE('" + dateStr + "'" + ",'%m/%d/%Y') "
				+ "GROUP BY ref_q_id order by COUNT(q_id) desc");
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.list();
		List<Question> listQuestion = new ArrayList<Question>();
		for (Object[] row : rows) {
			Long qid = Long.parseLong(row[0].toString());
			Question question = questionDao.getQuestionByID(qid);
			listQuestion.add(question);
		}
		return listQuestion;
	}
	
	@Override
	public List<QuestionHistory> getListRecentUnAnswer(Date from) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<QuestionHistory> questionHistories = session.createCriteria(QuestionHistory.class)
				.add(Restrictions.isNull("referenceQuestionId")).list();
		return questionHistories;
	}
	
	@Override
	public List<QuestionHistory> getListRecentQnA(Date from, int fetchSize, int firstResult) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<QuestionHistory> questionHistories = session.createCriteria(QuestionHistory.class)
				.add(Restrictions.gt("createdDate", from)).setFetchSize(fetchSize).setFirstResult(firstResult).list();
		return questionHistories;
	}
	
	@Override
	public void deleteQuestionHistory(Long historyId) {
		Session session = sessionFactory.getCurrentSession();
		QuestionHistory history = (QuestionHistory) session.get(QuestionHistory.class, historyId);
		session.delete(history);
	}
	
	@Override
	public void updateQuestionHistory(QuestionHistory questionHistory) {
		Session session = sessionFactory.getCurrentSession();
		session.update(questionHistory);
	}

	@Override
	public List<QuestionHistory> getHistoryByUserId(int userId) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<QuestionHistory> questionHistories = session.createCriteria(QuestionHistory.class)
				.add(Restrictions.eq("createUserId", userId)).list();
		return questionHistories;
	}
}
