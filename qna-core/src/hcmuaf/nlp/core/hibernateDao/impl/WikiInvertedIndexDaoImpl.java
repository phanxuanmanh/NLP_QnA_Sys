package hcmuaf.nlp.core.hibernateDao.impl;

import hcmuaf.nlp.core.dao.WikiInvertedIndexDao;
import hcmuaf.nlp.core.model.WikiInvertedIndex;
import hcmuaf.nlp.core.util.HibernateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class WikiInvertedIndexDaoImpl implements WikiInvertedIndexDao {
	SessionFactory sessionFactory;
	Session session;

	@Override
	public long numberOfConceptContainWord(int wordId) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		long countConcept = (long) session
				.createCriteria(WikiInvertedIndex.class)
				.add(Restrictions.eq("wordId", wordId))
				.setProjection(Projections.countDistinct("pageId"))
				.uniqueResult();
		tx.commit();
		session.close();
		return countConcept;
	}

	@Override
	public List<WikiInvertedIndex> listWordVector(int page) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<WikiInvertedIndex> listCOncept = session
				.createCriteria(WikiInvertedIndex.class)
				.add(Restrictions.eq("pageId", page)).list();
		tx.commit();
		session.close();
		return listCOncept;
	}

	@Override
	public List<WikiInvertedIndex> listWordVectorByWordId(int wordId) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<WikiInvertedIndex> listCOncept = session
				.createCriteria(WikiInvertedIndex.class)
				.add(Restrictions.eq("wordId", wordId)).list();
		tx.commit();
		session.close();
		return listCOncept;
	}

	

	@Override
	public long currentNumberOfConcept() {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		long countConcept = (long) session
				.createCriteria(WikiInvertedIndex.class)
				.setProjection(Projections.countDistinct("pageId"))
				.uniqueResult();
		tx.commit();
		session.close();
		return countConcept;
	}

	@Override
	public List<Integer> getListPageId() {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Integer> listCOncept = session
				.createCriteria(WikiInvertedIndex.class)
				.setProjection(
						Projections.distinct(Projections.property("pageId")))
				.addOrder(Order.asc("pageId")).list();
		tx.commit();
		session.close();
		return listCOncept;
	}

	

	@Override
	public long numberOfWordInPage(int pageId) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		long wordCount = (long) session.createCriteria(WikiInvertedIndex.class)
				.add(Restrictions.eq("pageId", pageId))
				.setProjection(Projections.sum("freq")).uniqueResult();
		tx.commit();
		session.close();
		return wordCount;
	}

	@Override
	public HashMap<Integer, Integer> getWordWithFreq() {
		HashMap<Integer, Integer> listWordFreq = new HashMap<Integer, Integer>();
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createSQLQuery("select word_id, count(page_id) from inverted_index  group by word_id");
		@SuppressWarnings("unchecked")
		List<Object[]> result = query.list();
		for (Object[] row : result) {
			Integer wordId = Integer.valueOf(row[0].toString());
			Integer freq = Integer.valueOf(row[1].toString());
			listWordFreq.put(wordId, freq);
		}
		tx.commit();
		System.out.println("load words with frequence done");
		session.close();
		return listWordFreq;
	}

	@Override
	public List<WikiInvertedIndex> getAllVector() {
		int numberConcept= currentNumberOfVector().intValue();
		List<WikiInvertedIndex> listAllCOncept = new ArrayList<WikiInvertedIndex>();
		sessionFactory = HibernateUtil.getSessionFactory();
		for(int i =1;i<numberConcept;i=i+50000){
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<WikiInvertedIndex> listConcept = session
					.createCriteria(WikiInvertedIndex.class).setFirstResult(i).setMaxResults(50000).list();
			tx.commit();
			session.close();
			listAllCOncept.addAll(listConcept);
		}
		
		return listAllCOncept;
	}

	@Override
	public Long currentNumberOfVector() {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Long wordCount =  (Long) session.createCriteria(WikiInvertedIndex.class)
				.setProjection(Projections.count("id")).uniqueResult();
		tx.commit();
		session.close();
		return wordCount;
	}

}
