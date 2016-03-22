package main.demo.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class WordAccessor {
	private static Connection con = null;

	public Set<String> getListkeyWord() {
		Set<String> setwords = new HashSet<String>();
			con = DbConnection.getConnection();
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

	public boolean addKeyWord(String keyWord) {
		boolean executeResult = false;
		if (con != null)
			con = DbConnection.getConnection();
		try {
			PreparedStatement pp = con
					.prepareStatement("insert into key_words(content) values (?)");
			pp.setString(1, keyWord);
			executeResult = pp.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}
		return executeResult;

	}

	public int getWordId(String keyword) {
		int wordId = 0;
		if (con != null)
			con = DbConnection.getConnection();
		try {
			PreparedStatement pp = con
					.prepareStatement("select wid from key_words where upper(content) = upper(?)");
			pp.setString(1, keyword);
			ResultSet rs = pp.executeQuery();
			while (rs.next()) {
				wordId = rs.getInt(1);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return wordId;
	}

	public void updateWordCount(int questionId, int wordId) {
		if (con != null)
			con = DbConnection.getConnection();
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
