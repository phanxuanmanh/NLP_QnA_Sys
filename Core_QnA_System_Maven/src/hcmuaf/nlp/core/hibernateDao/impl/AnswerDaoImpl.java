package hcmuaf.nlp.core.hibernateDao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import hcmuaf.nlp.core.dao.AnswerDao;
import hcmuaf.nlp.core.model.Answer;
import hcmuaf.nlp.core.util.HibernateUtil;

public class AnswerDaoImpl implements AnswerDao {
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	Session session = sessionFactory.getCurrentSession();

	@Override
	public int insertAnswer(String answer) {
		Transaction tx = session.beginTransaction();
		Answer ans = new Answer();
		ans.setContent(answer);
		session.persist(ans);
		tx.commit();
		return ans.getId();
	}

}
