/**
 * @author Manh Phan
 *
 * Edited date Mar 4, 2017
 */
package hcmuaf.nlp.core.hibernateDao.impl;

import hcmuaf.nlp.core.dao.AnswerDao;
import hcmuaf.nlp.core.model.Answer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class AnswerDaoImpl implements AnswerDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Long insertAnswer(String answer) {
		Session session = sessionFactory.getCurrentSession();
		Answer ans = new Answer();
		ans.setContent(answer);
		session.persist(ans);
		return ans.getId();
	}

	@Override
	public Answer getAnswer(Long answerId) {
		Session session = sessionFactory.getCurrentSession();
		Answer ans = (Answer) session.get(Answer.class, answerId);
		return ans;
	}
}
