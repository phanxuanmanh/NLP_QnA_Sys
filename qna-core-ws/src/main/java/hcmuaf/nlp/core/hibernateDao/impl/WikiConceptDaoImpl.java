package hcmuaf.nlp.core.hibernateDao.impl;

import hcmuaf.nlp.core.dao.WikiConceptDao;
import hcmuaf.nlp.core.model.DemoWikiPage;
import hcmuaf.nlp.core.model.WikiConceptWord;
import hcmuaf.nlp.core.model.WikiPage;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class WikiConceptDaoImpl implements WikiConceptDao {
	@Autowired
	SessionFactory sessionFactory;
	
	public WikiConceptDaoImpl() {
	}
	
	@Override
	public ArrayList<Integer> getConceptList() {
		ArrayList<Integer> listConcept = new ArrayList<Integer>();
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createSQLQuery("select DISTINCT p.page_latest from page p left outer join concepts_words c on p.page_latest = c.page_id where c.page_id is null and p.page_len >200 and p.page_in_process =1");
		@SuppressWarnings("unchecked")
		List<Integer> rows = query.list();
		for (int row : rows) {
			listConcept.add(row);
		}
		return listConcept;
	}
	
	@Override
	public String getConceptText(int page_latest) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createSQLQuery("SELECT cast(old_text as char(600000) character set utf8) FROM wikidb.text  where old_id =:latest");
		String data = query.setInteger("latest", page_latest).uniqueResult().toString();
		return data;
	}
	
	@Override
	public void updateWordCount(int page_latest, long wordId, int freq) {
		Session session = sessionFactory.getCurrentSession();
		WikiConceptWord vector = new WikiConceptWord();
		vector.setWordId(wordId);
		vector.setPageId(page_latest);
		vector.setFreq(freq);
		session.save(vector);
	}
	
	@Override
	public String getPageTitle(int pageID) {
		Session session = sessionFactory.getCurrentSession();
		String content = (String) session.createCriteria(WikiPage.class).add(Restrictions.eq("pageID", pageID))
				.setProjection(Projections.property("title")).uniqueResult();
		return content;
	}
	
	@Override
	public WikiPage getPage(int pageId) {
		Session session = sessionFactory.getCurrentSession();
		WikiPage page = (WikiPage) session.get(WikiPage.class, pageId);
		return page;
	}
	
	@Override
	public void updatePage(WikiPage wikiPage) {
		Session session = sessionFactory.getCurrentSession();
		session.update(wikiPage);
	}
	
	@Override
	public String getPageTitleByRefPageId(int pageID) {
		Session session = sessionFactory.getCurrentSession();
		DemoWikiPage demoPage = (DemoWikiPage) session.get(DemoWikiPage.class, pageID);
		if (demoPage != null) {
			return demoPage.getTitle();
		}
		return null;
	}
}
