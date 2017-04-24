package hcmuaf.nlp.core.hibernateDao.impl;

import hcmuaf.nlp.core.dao.AnswerDao;
import hcmuaf.nlp.core.dao.QnADao;
import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dto.QnAPair;
import hcmuaf.nlp.core.model.Answer;
import hcmuaf.nlp.core.model.QnAPairEntity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class QnADaoImpl implements QnADao {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private AnswerDao answerDao;
	public QnADaoImpl() {
	}

	@Override
	public void insertQnAPair(QnAPair pair) {
		Session session = sessionFactory.getCurrentSession();
		long q_id = questionDao.insertQuestion(pair.getQuestion(), pair.getTypeID());
		long a_id = answerDao.insertAnswer(pair.getAnswer());
		QnAPairEntity entity = new QnAPairEntity();
		entity.setQuestion(q_id);
		entity.setAnswer(a_id);
		session.persist(entity);

	}

	@Override
	public List<QnAPairEntity> getQnAPairs(Long questionId) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<QnAPairEntity> listQuestion = session.createCriteria(QnAPairEntity.class)
				.add(Restrictions.eq("question", questionId)).list();
		return listQuestion;
	}

	@Override
	public void insertQnAPair(QnAPairEntity pair) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(pair);

	}

	@Override
	public List<Answer> getAnswersByQuestionId(Long questionId) {
		List<QnAPairEntity> listQnA = getQnAPairs(questionId);
		List<Answer> answers = new ArrayList<Answer>();
		for (QnAPairEntity qna : listQnA) {
			Answer answer = answerDao.getAnswer(qna.getAnswer());
			answers.add(answer);
		}
		return answers;
	}

}
