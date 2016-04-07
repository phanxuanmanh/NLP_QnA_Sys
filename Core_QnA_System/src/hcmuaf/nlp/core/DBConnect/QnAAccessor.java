package hcmuaf.nlp.core.DBConnect;

import hcmuaf.nlp.core.model.QnAPair;
import hcmuaf.nlp.core.model.QuestionType;
import hcmuaf.nlp.core.model.QuestionVectorCsv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QnAAccessor {
	private static Connection con = null;

	public int insertQuestion(String question) {
		con = DbConnector.getConnection();
		int qid = 0;
		try {
			PreparedStatement pp_getid = con
					.prepareStatement("select max(q_id) from questions");
			PreparedStatement pp_insert = con
					.prepareStatement("insert into questions(q_id, q_content) values (?,?)");

			ResultSet rs_getid = pp_getid.executeQuery();
			while (rs_getid.next()) {
				qid = rs_getid.getInt(1) + 1;
			}
			pp_insert.setInt(1, qid);
			pp_insert.setString(2, question);
			pp_insert.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

		return qid;
	}

	public int insertAnswer(String answer) {
		con = DbConnector.getConnection();
		int aid = 0;
		try {
			PreparedStatement pp_getid = con
					.prepareStatement("select max(a_id) from answers");
			PreparedStatement pp_insert = con
					.prepareStatement(" insert into answers(a_id, a_content) values (?,?)");

			ResultSet rs_getid = pp_getid.executeQuery();
			while (rs_getid.next()) {
				aid = rs_getid.getInt(1) + 1;
			}
			pp_insert.setInt(1, aid);
			pp_insert.setString(2, answer);
			pp_insert.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

		return aid;
	}

	public boolean insertQnAPair(QnAPair pair) {
		int q_id = insertQuestion(pair.getQuestion());
		int a_id = insertAnswer(pair.getAnswer());
		if (q_id == 0 || a_id == 0) {
			System.out.println("inser error");
			return false;
		} else {
			if (con != null)
				con = DbConnector.getConnection();
			try {
				PreparedStatement pp = con
						.prepareStatement("insert into qna_pair(q_id,a_id,type_id) values (?,?,?)");
				pp.setInt(1, q_id);
				pp.setInt(2, a_id);
				pp.setInt(3, pair.getTypeID());
				pp.execute();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;

			}
		}

		return true;

	}

	public static String getQuestion(int qId) {
		String question = null;
		con = DbConnector.getConnection();
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

	public ArrayList<QuestionType> getTypeList() {
		ArrayList<QuestionType> typeList = new ArrayList<QuestionType>();
		con = DbConnector.getConnection();
		try {
			PreparedStatement pp = con
					.prepareStatement("select type_id, type_name from question_types");
			ResultSet result = pp.executeQuery();
			while (result.next()) {
				int typeID = result.getInt(1);
				String typeName = result.getString(2);
				QuestionType type = new QuestionType(typeID, typeName);
				typeList.add(type);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return typeList;
	}

	public static int getQuestionType(int qId) {
		int typeID = 0;
		con = DbConnector.getConnection();
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

	public static ArrayList<Integer> getQuestionList() {
		ArrayList<Integer> listID = new ArrayList<>();
		con = DbConnector.getConnection();
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

	// Read all question vector data
	public static ArrayList<QuestionVectorCsv> readQuestionVectorData()
			throws SQLException {
		ArrayList<QuestionVectorCsv> data = new ArrayList<>();
		ArrayList<Integer> qlist = getQuestionList();

		for (int i : qlist) {
			ArrayList<String> listWeight = new ArrayList<>();
			con = DbConnector.getConnection();
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

	public static QuestionVectorCsv readQuestionVectorData(int questionID)
			throws SQLException {
		;
		ArrayList<String> listWeight = new ArrayList<>();
		con = DbConnector.getConnection();
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
		System.out.println("question : " + questionID);
		int typeID = getQuestionType(questionID);
		QuestionVectorCsv vector =null;
		if (typeID>0) {
			vector=	 new QuestionVectorCsv(listWeight, typeID);
		}
		result.close();
		pp.close();
		con.close();

		return vector;
	}
}
