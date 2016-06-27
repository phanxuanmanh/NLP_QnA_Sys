package hcmuaf.nlp.core.hibernateDao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import hcmuaf.nlp.core.dao.AnswerDao;
import hcmuaf.nlp.core.dao.QnADao;
import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dto.QnAPair;
import hcmuaf.nlp.core.model.QnAPairEntity;
import hcmuaf.nlp.core.util.HibernateUtil;

public class QnADaoImpl implements QnADao {
	SessionFactory sessionFactory;
	Session session;

	public QnADaoImpl() {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
	}

	@Override
	public void insertQnAPair(QnAPair pair) {
		QuestionDao questionDao = new QuestionDaoImpl();
		AnswerDao answerDao = new AnswerDaoImpl();
		int q_id = questionDao.insertQuestion(pair.getQuestion(), pair.getTypeID());
		int a_id = answerDao.insertAnswer(pair.getAnswer());
		Transaction tx = session.beginTransaction();
		QnAPairEntity entity = new QnAPairEntity();
		entity.setQuestion(q_id);
		entity.setAnswer(a_id);
		session.persist(entity);
		tx.commit();
	}

}
