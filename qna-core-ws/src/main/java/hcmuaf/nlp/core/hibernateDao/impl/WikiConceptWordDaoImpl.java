package hcmuaf.nlp.core.hibernateDao.impl;

import hcmuaf.nlp.core.dao.WikiConceptWordDao;
import hcmuaf.nlp.core.model.WikiConceptWord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class WikiConceptWordDaoImpl implements WikiConceptWordDao {
	private static final double MIN_TFIDF_THRESHOLD = 0.005d;
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public long numberOfConceptContainWord(int wordId) {
		Session session = sessionFactory.getCurrentSession();
		long countConcept = (long) session.createCriteria(WikiConceptWord.class).add(Restrictions.eq("wordId", wordId))
				.setProjection(Projections.countDistinct("pageId")).uniqueResult();
		return countConcept;
	}
	
	@Override
	public List<WikiConceptWord> listWordVectorByPage(int page) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<WikiConceptWord> listCOncept = session.createCriteria(WikiConceptWord.class)
				.add(Restrictions.eq("pageId", page)).list();
		return listCOncept;
	}
	
	@Override
	public long currentNumberOfConcept() {
		Session session = sessionFactory.getCurrentSession();
		long countConcept = (long) session.createCriteria(WikiConceptWord.class)
				.setProjection(Projections.countDistinct("pageId")).uniqueResult();
		return countConcept;
	}
	
	@Override
	public List<Integer> getListPageId() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Integer> listCOncept = session.createCriteria(WikiConceptWord.class)
				.setProjection(Projections.distinct(Projections.property("pageId"))).addOrder(Order.asc("pageId"))
				.list();
		return listCOncept;
	}
	
	@Override
	public void updateConceptWord(WikiConceptWord w) {
		Session session = sessionFactory.getCurrentSession();
		session.update(w);
	}
	
	@Override
	public long numberOfWordInPage(int pageId) {
		Session session = sessionFactory.getCurrentSession();
		long wordCount = (long) session.createCriteria(WikiConceptWord.class).add(Restrictions.eq("pageId", pageId))
				.setProjection(Projections.sum("freq")).uniqueResult();
		return wordCount;
	}
	
	@Override
	public HashMap<Long, Integer> getWordWithFreq() {
		HashMap<Long, Integer> listWordFreq = new HashMap<Long, Integer>();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select word_id, count(page_id) "
				+ "from concepts_words  group by word_id");
		@SuppressWarnings("unchecked")
		List<Object[]> result = query.list();
		for (Object[] row : result) {
			Long wordId = Long.valueOf(row[0].toString());
			Integer freq = Integer.valueOf(row[1].toString());
			listWordFreq.put(wordId, freq);
		}
		System.out.println("load words with frequence done");
		return listWordFreq;
	}
	
	@Override
	public List<WikiConceptWord> listWordVectorByWordId(int wordId) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<WikiConceptWord> listCOncept = session.createCriteria(WikiConceptWord.class)
				.add(Restrictions.eq("wordId", wordId)).list();
		return listCOncept;
	}
	
	@Override
	public void updateListConceptWord(List<WikiConceptWord> listWordVectors) {
		Session session = sessionFactory.getCurrentSession();
		for (WikiConceptWord vector : listWordVectors) {
			session.update(vector);
		}
	}
	
	@Override
	public List<WikiConceptWord> listWordVectorByListWordId(List<Integer> listWordIds) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM WikiConceptWord where wordId IN (:ids)");
		query.setParameterList("ids", listWordIds);
		@SuppressWarnings("unchecked")
		List<WikiConceptWord> listCOncept = query.list();
		return listCOncept;
	}
	
	@Override
	public void saveWordCount(HashMap<Long, Integer> listWordWithFreq, int pageId) {
		List<Entry<Long, Integer>> listEntry = new ArrayList<Entry<Long, Integer>>(listWordWithFreq.entrySet());
		Session session = sessionFactory.getCurrentSession();
		for (Entry<Long, Integer> entry : listEntry) {
			WikiConceptWord vector = new WikiConceptWord();
			vector.setWordId(entry.getKey());
			vector.setPageId(pageId);
			vector.setFreq(entry.getValue().intValue());
			session.persist(vector);
		}
	}
	
	@Override
	public List<WikiConceptWord> getAllVector() {
		List<WikiConceptWord> listAllCOncept = new ArrayList<>();
		StatelessSession session = sessionFactory.openStatelessSession();
		Query query = 
				session.createQuery("from WikiConceptWord cw where tfidf > "+ MIN_TFIDF_THRESHOLD)
				.setCacheable(false)
				.setFetchSize(Integer.MIN_VALUE)
				.setReadOnly(true);
		ScrollableResults results = query.scroll(ScrollMode.FORWARD_ONLY);
		while(results.next()){
			Object[] row = results.get();
			if(row.length>0){
				WikiConceptWord wikiConceptWord =(WikiConceptWord) row[0];
				listAllCOncept.add(wikiConceptWord);
			}
		}
		results.close();
		session.close();
		return listAllCOncept;
	}
	
	@Override
	public Long currentNumberOfVector() {
		Session session = sessionFactory.getCurrentSession();
		Long wordCount = (Long) session.createCriteria(WikiConceptWord.class)
				.add(Restrictions.gt("tfidf", MIN_TFIDF_THRESHOLD))
				.setProjection(Projections.count("id"))
				.uniqueResult();
		return wordCount;
	}
}
