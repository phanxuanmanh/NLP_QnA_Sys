package hcmuaf.nlp.core.jdbcDao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import hcmuaf.nlp.core.DBConnect.DbConnector;
import hcmuaf.nlp.core.dao.KeyWordDao;
import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.model.QuestionVectorCsv;

public class QuestionDaoImpl implements QuestionDao {
	@Override
	public ArrayList<Integer> getQuestionList() {
		ArrayList<Integer> listID = new ArrayList<>();
		Connection con = DbConnector.getConnection();
		try {
			PreparedStatement pp = con
					.prepareStatement("select q_id from questions");
			ResultSet result = pp.executeQuery();
			while (result.next()) {
				int qid = result.getInt(1);
				listID.add(qid);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return listID;
	}

	@Override
	public int countQuestion() {
		int wid = 0;
		Connection con = DbConnector.getConnection();
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

	@Override
	public int getQuestionType(int qId) {
		int typeID = 0;
		Connection con = DbConnector.getConnection();
		try {
			PreparedStatement pp = con
					.prepareStatement("select type_id from qna_pair where q_id = ?");
			pp.setInt(1, qId);
			ResultSet result = pp.executeQuery();
			while (result.next()) {
				typeID = result.getInt(1);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return typeID;
	}

	@Override
	public String getQuestion(int qId) {
		String question = null;
		Connection con = DbConnector.getConnection();
		try {
			PreparedStatement pp = con
					.prepareStatement("select q_content from questions where q_id = ?");
			pp.setInt(1, qId);
			ResultSet result = pp.executeQuery();
			while (result.next()) {
				question = result.getString(1);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return question;
	}

	@Override
	public int insertQuestion(String question,int typeID) {
		Connection con = DbConnector.getConnection();
		int qid = 0;
		try {
			PreparedStatement pp_getid = con
					.prepareStatement("select max(q_id) from questions");
			PreparedStatement pp_insert = con
					.prepareStatement("insert into questions(q_id, q_content,type_id) values (?,?,?)");

			ResultSet rs_getid = pp_getid.executeQuery();
			while (rs_getid.next()) {
				qid = rs_getid.getInt(1) + 1;
			}
			pp_insert.setInt(1, qid);
			pp_insert.setString(2, question);
			pp_insert.setInt(3, typeID);
			pp_insert.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

		return qid;
	}

	@Override
	public QuestionVectorCsv readQuestionVectorData(int questionID)
			throws SQLException {
		ArrayList<String> listWeight = new ArrayList<>();
		Connection con = DbConnector.getConnection();
		PreparedStatement pp = con
				.prepareStatement("SELECT wid, SUM(weight) AS weight FROM"
						+ " (SELECT kw.wid, 0.0 AS weight FROM  key_words kw 	"
						+ "UNION     SELECT  kw.wid, v.weight FROM  key_words kw, question_vectors v    "
						+ "WHERE        v.q_id = ? AND kw.wid = v.wid) t GROUP BY wid ORDER BY wid");
		pp.setInt(1, questionID);
		ResultSet result = pp.executeQuery();
		while (result.next()) {
			double weight = result.getDouble(2);
			listWeight.add(String.valueOf(weight));
		}
		int typeID = getQuestionType(questionID);
		QuestionVectorCsv vector = null;
		if (typeID > 0) {
			vector = new QuestionVectorCsv(listWeight, typeID);
		}
		result.close();
		pp.close();
		con.close();

		return vector;
	}

	@Override
	public ArrayList<QuestionVectorCsv> readQuestionVectorData()
			throws SQLException {
		ArrayList<QuestionVectorCsv> data = new ArrayList<>();
		ArrayList<Integer> qlist = getQuestionList();

		for (int i : qlist) {
			ArrayList<String> listWeight = new ArrayList<>();
			Connection con = DbConnector.getConnection();
			PreparedStatement pp = con
					.prepareStatement("SELECT wid, SUM(weight) AS weight FROM"
							+ " (SELECT kw.wid, 0.0 AS weight FROM  key_words kw 	"
							+ "UNION     SELECT  kw.wid, v.weight FROM  key_words kw, question_vectors v    "
							+ "WHERE        v.q_id = ? AND kw.wid = v.wid) t GROUP BY wid ORDER BY wid");
			pp.setInt(1, i);
			ResultSet result = pp.executeQuery();
			while (result.next()) {
				double weight = result.getDouble(2);
				listWeight.add(String.valueOf(weight));
			}
			System.out.println("question : " + i);
			int typeID = getQuestionType(i);
			QuestionVectorCsv vector = new QuestionVectorCsv(listWeight, typeID);
			data.add(vector);
			result.close();
			pp.close();
			con.close();
		}
		return data;
	}

	@Override
	public void updateWordCount(int questionId, int wordId) {
		Connection con = DbConnector.getConnection();
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

	@Override
	public int numOfQuestionContainWord(int wid) {
		KeyWordDao keyWordDao = new KeyWordDaoImpl();
		return keyWordDao.numOfQuestionContainWord(wid);
	}

}
