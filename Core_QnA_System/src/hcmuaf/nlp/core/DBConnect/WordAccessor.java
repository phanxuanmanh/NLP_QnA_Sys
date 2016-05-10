package hcmuaf.nlp.core.DBConnect;

import hcmuaf.nlp.core.model.Keyword;
import hcmuaf.nlp.core.model.QuestionVector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class WordAccessor {
	private static Connection con = null;
	private static HashMap<String, Integer> listWord;

	public WordAccessor() {
		listWord = getListkeyWord3();
	}

	public static HashMap<String, Integer> getListWord() {
		return listWord;
	}

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

	public static ArrayList<Keyword> getListkeyWord2() {
		ArrayList<Keyword> listWord = new ArrayList<Keyword>();
		con = DbConnector.getConnection();
		try {
			PreparedStatement pp = con
					.prepareStatement("select wid,content from key_words order by wid asc");
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

	public HashMap<String, Integer> getListkeyWord3() {
		HashMap<String, Integer> listWord = new HashMap<String, Integer>();
		con = DbConnector.getConnection();
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

	public int addKeyWord(String keyWord) throws SQLException {
		int wid = 0;
		con = DbConnector.getConnection();
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

	public int getWordId(String keyword) throws SQLException {
		Integer wordId = listWord.get(keyword);
		if (wordId != null) {
			return wordId.intValue();
		} else {
			int newID = addKeyWord(keyword);
			return newID;
		}

	}

	// update word count for question
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

	// count number of question in the system
	public static int countQuestion() {
		int wid = 0;
		con = DbConnector.getConnection();
		try {
			PreparedStatement pp2 = con
					.prepareStatement("select count(*) from qna_pair");
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

	// Count number of question that a word exist
	public static int numOfQuestionContainWord(int wid) {
		int freq = 0;
		con = DbConnector.getConnection();
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

	// Update IDF weight for a keyword
	public static void updateIDF(double idf, int wid) {
		con = DbConnector.getConnection();
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

	// Update IDF weight for a keyword
	public static double getIDF(int wid) {
		double idf = 0.0;
		con = DbConnector.getConnection();
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

	// Get list of question vectors
	public static ArrayList<QuestionVector> getListVector(int questionID) {
		ArrayList<QuestionVector> listVector = new ArrayList<>();
		con = DbConnector.getConnection();
		try {
			PreparedStatement pp = con
					.prepareStatement("SELECT q_id, wid, weight, freq FROM question_vectors where q_id =?");
			pp.setInt(1, questionID);
			ResultSet rs = pp.executeQuery();
			while (rs.next()) {
				int qid = rs.getInt(1);
				int wordID = rs.getInt(2);
				double weight = rs.getDouble(3);
				int frq = rs.getInt(4);
				QuestionVector vector = new QuestionVector(qid, wordID, frq,
						weight);
				listVector.add(vector);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return listVector;
	}

	// update weight for a keyword
	public static void updateTFIDF(int qid, int wid, double tfidf) {
		con = DbConnector.getConnection();
		try {
			PreparedStatement pp3 = con
					.prepareStatement("UPDATE question_vectors SET weight=? WHERE q_id=? and wid =?");
			pp3.setDouble(1, tfidf);
			pp3.setInt(2, qid);
			pp3.setInt(3, wid);
			pp3.execute();
			pp3.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

}
