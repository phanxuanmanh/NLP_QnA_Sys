package hcmuaf.nlp.core.hibernateDao.impl;

import hcmuaf.nlp.core.dao.KeyWordDao;
import hcmuaf.nlp.core.model.Keyword;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Repository
@Transactional
public class KeyWordDaoImpl implements KeyWordDao {
	@Autowired
	SessionFactory sessionFactory;
	
	public KeyWordDaoImpl() {
	}
	
	@Override
	public Map<String, Long> getMapWordByContent() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Keyword> lisKeyWord = session.createCriteria(Keyword.class).list();
		return lisKeyWord.stream().collect(
				Collectors.toMap(keyword -> keyword.getContent().toUpperCase(), keyword -> keyword.getId()));
	}
	
	@Override
	public Set<String> getListKeyWordContent() {
		Session session = sessionFactory.getCurrentSession();
		Set<String> setwords = new HashSet<String>();
		Query query = session.createSQLQuery("select content from key_words");
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.list();
		for (Object[] row : rows) {
			String content = row[0].toString().toUpperCase();
			setwords.add(content);
		}
		return setwords;
	}
	
	@Override
	public ArrayList<Keyword> getListKeyWord() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select wid,content from key_words");
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.list();
		List<Keyword> setwords = rows.parallelStream().map(this::rowDataToKeyWord).collect(Collectors.toList());
		return new ArrayList<Keyword>(setwords);
	}
	
	private Keyword rowDataToKeyWord(Object[] row) {
		long wid = Long.parseLong(row[0].toString());
		String content = row[1].toString().toUpperCase();
		Keyword keyWord = new Keyword();
		keyWord.setId(wid);
		keyWord.setContent(content);
		return keyWord;
	}
	
	@Override
	public Long addKeyWord(String keyWord) {
		Session session = sessionFactory.getCurrentSession();
		Keyword word = new Keyword();
		word.setContent(keyWord);
		session.persist(word);
		return word.getId();
	}
	
	@Override
	public Long getOrSaveKeyWord(String keyword) {
		Long wordId = getKeyWordId(keyword);
		if (wordId!=null) {
			return wordId;
		} else {
			return addKeyWord(keyword);
		}
	}

	@Override
	public Long getKeyWordId(String keyword) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Keyword> mappedKeyWords = session.createCriteria(Keyword.class)
				.add(Restrictions.eq("content", keyword).ignoreCase()).list();
		if (!CollectionUtils.isEmpty(mappedKeyWords)) {
			return mappedKeyWords.get(0).getId();
		}
		return null;
	}
}
