package hcmuaf.nlp.core.DBConnect;

import hcmuaf.nlp.core.model.Keyword;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class WordAccessor {
	private static Connection con = null;

	public Set<String> getListkeyWord() {
		Set<String> setwords = new HashSet<String>();
		con = DbConnector.getConnection();
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

	public ArrayList<Keyword> getListkeyWord2() {
		ArrayList<Keyword> listWord = new ArrayList<Keyword>();
		con = DbConnector.getConnection();
		try {
			PreparedStatement pp = con
					.prepareStatement("select wid,content from key_words");
			ResultSet rs = pp.executeQuery();
			while (rs.next()) {
				int wordID = rs.getInt(1);
				String content = rs.getString(2);
				Keyword kw = new Keyword(content, wordID);
				listWord.add(kw);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return listWord;
	}

	public ArrayList<Keyword> getListkeyWord(String wcontent) {
		ArrayList<Keyword> listWord = new ArrayList<Keyword>();
		con = DbConnector.getConnection();
		try {
			PreparedStatement pp = con
					.prepareStatement("select wid,content from key_words where upper(content) = upper(?)");
			pp.setString(1, wcontent);
			ResultSet rs = pp.executeQuery();
			while (rs.next()) {
				int wordID = rs.getInt(1);
				String content = rs.getString(2);
				Keyword kw = new Keyword(content, wordID);
				listWord.add(kw);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return listWord;
	}

	public int addKeyWord(String keyWord) {
		int wid = 0;
		con = DbConnector.getConnection();
		try {
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
			if (wid > 0)
				return wid;
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;

		}
		return wid;

	}

	public int getWordId(String keyword) {
		int wordId = 0;
		ArrayList<Keyword> listKW = getListkeyWord(keyword);
		for (Keyword k : listKW) {
			if (k.getContent().toUpperCase().equals(keyword.toUpperCase())) {
				wordId = k.getId();
			}
		}
		if (wordId == 0) {
			wordId = addKeyWord(keyword);
		}
		return wordId;
	}

	public void updateWordCount(int questionId, int wordId) {
			con = DbConnector.getConnection();
		try {
			PreparedStatement pp = con
					.prepareStatement("select * from  question_vectors where q_id =? and wid = ?");
			pp.setInt(1, questionId);
			pp.setInt(2, wordId);
			ResultSet result = pp.executeQuery();
			if (result.next()) {
				PreparedStatement pp3 = con
						.prepareStatement("update question_vectors set freq =freq+ 1  where q_id =? and wid = ?");
				pp3.setInt(1, questionId);
				pp3.setInt(2, wordId);
				pp3.execute();
				pp3.close();
			} else {
				PreparedStatement pp2 = con
						.prepareStatement("insert into question_vectors(q_id,wid,freq) values (?,?,?)");
				pp2.setInt(1, questionId);
				pp2.setInt(2, wordId);
				pp2.setInt(3, 1);
				pp2.execute();
				pp2.close();
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

}
