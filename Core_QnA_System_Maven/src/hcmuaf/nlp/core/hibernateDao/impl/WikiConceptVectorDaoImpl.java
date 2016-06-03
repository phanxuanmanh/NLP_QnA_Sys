package hcmuaf.nlp.core.hibernateDao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import hcmuaf.nlp.core.dao.WikiConceptVectorDao;
import hcmuaf.nlp.core.model.WikiConceptWord;
import hcmuaf.nlp.core.util.HibernateUtil;

public class WikiConceptVectorDaoImpl implements WikiConceptVectorDao {
	SessionFactory sessionFactory;
	Session session;

	@Override
	public long numberOfConceptContainWord(int wordId) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		long countConcept = (long) session
				.createCriteria(WikiConceptWord.class).setCacheable(true)
				.add(Restrictions.eq("wordId", wordId))
				.setProjection(Projections.countDistinct("pageId"))
				.uniqueResult();
		tx.commit();
		return countConcept;
	}

	@Override
	public List<WikiConceptWord> listWordVector(int page) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<WikiConceptWord> listCOncept = session
				.createCriteria(WikiConceptWord.class).setCacheable(true)
				.add(Restrictions.eq("pageId", page)).list();
		tx.commit();
		return listCOncept;
	}

	@Override
	public long currentNumberOfConcept() {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		long countConcept = (long) session
				.createCriteria(WikiConceptWord.class)
				.setProjection(Projections.countDistinct("pageId"))
				.uniqueResult();
		tx.commit();
		return countConcept;
	}

	@Override
	public List<Integer> getListPageId() {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Integer> listCOncept = session
				.createCriteria(WikiConceptWord.class)
				.setProjection(
						Projections.distinct(Projections.property("pageId")))
				.addOrder(Order.asc("pageId")).list();
		tx.commit();
		return listCOncept;
	}

	@Override
	public void updateConceptWord(WikiConceptWord w) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(w);
		tx.commit();
		session.close();
	}

	@Override
	public long numberOfWordInPage(int pageId) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		long wordCount = (long) session.createCriteria(WikiConceptWord.class)
				.setCacheable(true).add(Restrictions.eq("pageId", pageId))
				.setProjection(Projections.sum("freq")).uniqueResult();
		tx.commit();

		return wordCount;
	}

	@Override
	public HashMap<Integer, Integer> getWordWithFreq() {
		HashMap<Integer, Integer> listWordFreq = new HashMap<Integer, Integer>();
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createSQLQuery("select word_id, count(page_id) from concepts_words  group by word_id");
		@SuppressWarnings("unchecked")
		List<Object[]> result = query.list();
		for (Object[] row : result) {
			Integer wordId = Integer.valueOf(row[0].toString());
			Integer freq = Integer.valueOf(row[1].toString());
			listWordFreq.put(wordId, freq);
		}
		tx.commit();
		System.out.println("load words with frequence done");
		return listWordFreq;
	}

	@Override
	public List<WikiConceptWord> listWordVectorByWordId(int wordId) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<WikiConceptWord> listCOncept = session
				.createCriteria(WikiConceptWord.class)
				.add(Restrictions.eq("wordId", wordId)).list();
		session.close();
		tx.commit();
		return listCOncept;
	}

	@Override
	public void updateListConceptWord(List<WikiConceptWord> listWordVectors) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		for (WikiConceptWord vector : listWordVectors) {
			session.update(vector);
		}
		tx.commit();
		session.close();
	}

}
