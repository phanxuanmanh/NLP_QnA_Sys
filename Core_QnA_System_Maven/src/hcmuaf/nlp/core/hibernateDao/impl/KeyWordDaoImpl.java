package hcmuaf.nlp.core.hibernateDao.impl;

import hcmuaf.nlp.core.dao.KeyWordDao;
import hcmuaf.nlp.core.model.Keyword;
import hcmuaf.nlp.core.util.HibernateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class KeyWordDaoImpl implements KeyWordDao {
	SessionFactory sessionFactory;
	Session session;
	private static HashMap<String, Integer> listWord;

	public KeyWordDaoImpl() {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
		listWord = getListWord();
	}

	@Override
	public HashMap<String, Integer> getListWord() {
		HashMap<String, Integer> listWord = new HashMap<String, Integer>();
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createSQLQuery("select wid,content from key_words order by wid asc");
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.list();
		for (Object[] row : rows) {
			int wordID = Integer.parseInt(row[0].toString());
			String content = row[1].toString();
			listWord.put(content.toUpperCase(), new Integer(wordID));
		}
		tx.commit();
		return listWord;
	}

	@Override
	public Set<String> getListkeyWord() {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
		Set<String> setwords = new HashSet<String>();
		Transaction tx = session.beginTransaction();
		Query query = session.createSQLQuery("select content from key_words");
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.list();
		for (Object[] row : rows) {
			String content = row[0].toString().toUpperCase();
			setwords.add(content);
		}
		tx.commit();
		return setwords;
	}

	@Override
	public ArrayList<Keyword> getListkeyWord2() {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
		ArrayList<Keyword> setwords = new ArrayList<Keyword>();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createSQLQuery("select wid,content,idf from key_words");
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.list();
		for (Object[] row : rows) {
			int wid = Integer.parseInt(row[0].toString());
			String content = row[1].toString().toUpperCase();
			double idf = Double.parseDouble(row[2].toString());
			Keyword keyWord = new Keyword();
			keyWord.setId(wid);
			keyWord.setContent(content);
			keyWord.setIdf(idf);
			setwords.add(keyWord);

		}
		tx.commit();
		return setwords;
	}

	@Override
	public ArrayList<Keyword> getListkeyWord(String wcontent) {
		ArrayList<Keyword> listWord = new ArrayList<Keyword>();
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createSQLQuery("select wid,content from key_words where upper(content) = upper(:wcontent)");
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.setString("wcontent", wcontent).list();
		for (Object[] row : rows) {
			int wordID = Integer.parseInt(row[0].toString());
			String content = row[1].toString();
			Keyword keyWord = new Keyword();
			keyWord.setId(wordID);
			keyWord.setContent(content);
			listWord.add(keyWord);
		}
		tx.commit();
		return listWord;
	}

	@Override
	public int addKeyWord(String keyWord) throws SQLException {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Keyword word = new Keyword();
		word.setContent(keyWord);
		session.persist(word);
		tx.commit();
		return word.getId();
	}

	@Override
	public int getWordId(String keyword) throws SQLException {
		Integer wordId = listWord.get(keyword.toUpperCase());
		if (wordId != null) {
			return wordId.intValue();
		} else {
			int newID = addKeyWord(keyword);
			listWord.put(keyword.toUpperCase(), Integer.valueOf(newID));
			return newID;
		}

	}

	@Override
	public int numOfQuestionContainWord(int wid) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createSQLQuery("SELECT count(*) FROM question_vectors WHERE wid =:wordID");
		int numberOfQuestion = Integer.parseInt(query
				.setInteger(":wordID", new Integer(wid)).uniqueResult()
				.toString());

		tx.commit();
		return numberOfQuestion;
	}

	@Override
	public void updateIDF(double idf, int wid) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Keyword word = getKeyWord(wid);
		word.setIdf(idf);
		session.update(word);
		tx.commit();
	}

	@Override
	public double getIDF(int wid) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createSQLQuery("SELECT IDF from key_words WHERE wid =:wordID");
		int idf = Integer.parseInt(query
				.setInteger(":wordID", new Integer(wid)).uniqueResult()
				.toString());

		tx.commit();
		return idf;
	}

	@Override
	public Keyword getKeyWord(int wid) {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
		Keyword keyWord = (Keyword) session.get(Keyword.class, wid);
		return keyWord;
	}

}
