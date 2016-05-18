package hcmuaf.nlp.core.hibernateDao.impl;

import hcmuaf.nlp.core.dao.WikiConceptDao;
import hcmuaf.nlp.core.model.ConceptWordPair;
import hcmuaf.nlp.core.model.WikiConceptWord;
import hcmuaf.nlp.core.model.WikiPage;
import hcmuaf.nlp.core.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class WikiConceptDaoImpl implements WikiConceptDao {
	SessionFactory sessionFactory;
	Session session;

	public WikiConceptDaoImpl() {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
	}

	@Override
	public ArrayList<Integer> getConCeptList() {
		ArrayList<Integer> listConcept = new ArrayList<Integer>();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createSQLQuery("SELECT page_latest FROM wikidb.page  where page_len>200");
		@SuppressWarnings("unchecked")
		List<Integer> rows = query.list();
		for (int row : rows) {
			listConcept.add(row);

		}
		tx.commit();
		return listConcept;
	}

	@Override
	public String getConceptText(int page_latest) {
		Transaction tx = session.beginTransaction();
		Query query = session
				.createSQLQuery("SELECT cast(old_text as char(600000) character set utf8) FROM wikidb.text  where old_id =:latest");
		String data = query.setInteger("latest", page_latest).uniqueResult()
				.toString();

		tx.commit();
		return data;
	}

	@Override
	public void updateWordCount(int page_latest, int wordId, int freq) {
		Transaction tx = session.beginTransaction();
		WikiConceptWord vector = (WikiConceptWord) session
				.get(WikiConceptWord.class, new ConceptWordPair(page_latest,
						wordId));
		vector.setFreq(vector.getFreq() + 1);
		session.update(vector);
		tx.commit();
	}

	@Override
	public String getPageTitle(int pageID) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		String content = (String) session.createCriteria(WikiPage.class)
				.add(Restrictions.eq("pageID", pageID))
				.setProjection(Projections.property("title")).uniqueResult();
		tx.commit();
		return content;
	}

}
