package main.demo.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QnA_Accessor {
	private static Connection con = null;

	public int insertQuestion(String question) {
		con = DbConnection.getConnection();
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
		con = DbConnection.getConnection();
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

	public boolean insertQnAPair(String question, String answer) {
		int q_id = insertQuestion(question);
		int a_id = insertAnswer(answer);
		if (q_id == 0 || a_id == 0) {
			System.out.println("inser error");
			return false;
		} else {
			if (con != null)
				con = DbConnection.getConnection();
			try {
				PreparedStatement pp = con
						.prepareStatement("insert into qna_pair(q_id,a_id) values (?,?)");
				pp.setInt(1, q_id);
				pp.setInt(2, a_id);
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
		con = DbConnection.getConnection();
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

}
