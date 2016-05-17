package hcmuaf.nlp.core.jdbcDao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import hcmuaf.nlp.core.DBConnect.DbConnector;
import hcmuaf.nlp.core.dao.KeyWordDao;
import hcmuaf.nlp.core.model.Keyword;

public class KeyWordDaoImpl implements KeyWordDao {
	private static HashMap<String, Integer> listWord;

	public KeyWordDaoImpl() {
		listWord = getListWord();
	}

	@Override
	public HashMap<String, Integer> getListWord() {
		HashMap<String, Integer> listWord = new HashMap<String, Integer>();
		Connection con = DbConnector.getConnection();
		try {
			PreparedStatement pp = con
					.prepareStatement("select wid,content from key_words order by wid asc");
			ResultSet rs = pp.executeQuery();
			while (rs.next()) {
				int wordID = rs.getInt(1);
				String content = rs.getString(2);
				listWord.put(content, new Integer(wordID));
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return listWord;
	}

	@Override
	public Set<String> getListkeyWord() {
		Set<String> setwords = new HashSet<String>();
		Connection con = DbConnector.getConnection();
		try {
			PreparedStatement pp = con
					.prepareStatement("select content from key_words");
			ResultSet rs = pp.executeQuery();
			while (rs.next()) {
				setwords.add(rs.getString(1).toUpperCase());
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return setwords;
	}

	@Override
	public ArrayList<Keyword> getListkeyWord(String wcontent) {
		ArrayList<Keyword> listWord = new ArrayList<Keyword>();
		Connection con = DbConnector.getConnection();
		try {
			PreparedStatement pp = con
					.prepareStatement("select wid,content from key_words where upper(content) = upper(?)");
			pp.setString(1, wcontent);
			ResultSet rs = pp.executeQuery();
			while (rs.next()) {
				int wordID = rs.getInt(1);
				String content = rs.getString(2);
				Keyword kw = new Keyword();
				kw.setId(wordID);
				kw.setContent(content);
				listWord.add(kw);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return listWord;
	}

	@Override
	public int addKeyWord(String keyWord) throws SQLException {
		int wid = 0;
		Connection con = DbConnector.getConnection();
		PreparedStatement pp = con
				.prepareStatement("insert into key_words(content) values (?)");
		pp.setString(1, keyWord);
		pp.execute();
		PreparedStatement pp2 = con
				.prepareStatement("select max(wid) from key_words");
		ResultSet result = pp2.executeQuery();
		while (result.next()) {
			wid = result.getInt(1);
		}
		if (wid > 0) {
			listWord.put(keyWord, new Integer(wid));
			return wid;
		}

		con.close();
		return wid;
	}

	@Override
	public int getWordId(String keyword) throws SQLException {
		Integer wordId = listWord.get(keyword);
		if (wordId != null) {
			return wordId.intValue();
		} else {
			int newID = addKeyWord(keyword);
			return newID;
		}
	}

	@Override
	public int numOfQuestionContainWord(int wid) {
		int freq = 0;
		Connection con = DbConnector.getConnection();
		try {
			PreparedStatement pp2 = con
					.prepareStatement("SELECT count(*) FROM question_vectors WHERE wid =?");
			pp2.setInt(1, wid);
			ResultSet result = pp2.executeQuery();
			while (result.next()) {
				freq = result.getInt(1);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;

		}
		return freq;
	}

	@Override
	public void updateIDF(double idf, int wid) {
		Connection con = DbConnector.getConnection();
		try {
			PreparedStatement pp3 = con
					.prepareStatement("UPDATE key_words SET IDF=? WHERE wid =?");
			pp3.setDouble(1, idf);
			pp3.setInt(2, wid);
			pp3.execute();
			pp3.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	@Override
	public double getIDF(int wid) {
		double idf = 0.0;
		Connection con = DbConnector.getConnection();
		try {
			PreparedStatement pp3 = con
					.prepareStatement("SELECT IDF from key_words WHERE wid =?");
			pp3.setInt(1, wid);
			ResultSet result = pp3.executeQuery();
			while (result.next()) {
				idf = result.getDouble(1);
			}
			pp3.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return idf;
	}

	@Override
	public ArrayList<Keyword> getListkeyWord2() {
		ArrayList<Keyword> listWord = new ArrayList<Keyword>();
		Connection con = DbConnector.getConnection();
		try {
			PreparedStatement pp = con
					.prepareStatement("select wid,content from key_words order by wid asc");
			ResultSet rs = pp.executeQuery();
			while (rs.next()) {
				int wordID = rs.getInt(1);
				String content = rs.getString(2);
				Keyword kw = new Keyword();
				kw.setId(wordID);
				kw.setContent(content);
				listWord.add(kw);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return listWord;
	}

	@Override
	public Keyword getKeyWord(int wid) {
		// TODO Auto-generated method stub
		return null;
	}

}
