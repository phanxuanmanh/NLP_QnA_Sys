package hcmuaf.nlp.core.jdbcDao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import hcmuaf.nlp.core.DBConnect.DbConnector;
import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dao.QuestionTypeDao;
import hcmuaf.nlp.core.dao.QuestionVectorDao;
import hcmuaf.nlp.core.model.QuestionVector;
import hcmuaf.nlp.core.model.QuestionVectorCsv;

public class QuestionVectorDaoImpl implements QuestionVectorDao {

	@Override
	public void updateTFIDF(int qid, int wid, double tfidf) {
		Connection con = DbConnector.getConnection();
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

	@Override
	public ArrayList<QuestionVector> getListVector(int questionID) {
		ArrayList<QuestionVector> listVector = new ArrayList<>();
		Connection con = DbConnector.getConnection();
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
	public ArrayList<QuestionVectorCsv> readQuestionVectorData()
			throws SQLException {
		QuestionDao questionDao = new QuestionDaoImpl();
		QuestionTypeDao questionTypeDao = new QuestionTypeDaoImpl();
		ArrayList<QuestionVectorCsv> data = new ArrayList<>();
		ArrayList<Integer> qlist = questionDao.getQuestionList();

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
			int typeID = questionTypeDao.getQuestionType(i);
			QuestionVectorCsv vector = new QuestionVectorCsv(listWeight, typeID);
			data.add(vector);
			result.close();
			pp.close();
			con.close();
		}
		return data;
	}

	@Override
	public QuestionVectorCsv readQuestionVectorData(int questionID)
			throws SQLException {
		QuestionTypeDao questionTypeDao = new QuestionTypeDaoImpl();
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
		int typeID = questionTypeDao.getQuestionType(questionID);
		QuestionVectorCsv vector = null;
		if (typeID > 0) {
			vector = new QuestionVectorCsv(listWeight, typeID);
		}
		result.close();
		pp.close();
		con.close();
		return vector;
	}

}
