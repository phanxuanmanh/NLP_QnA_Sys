package hcmuaf.nlp.core.jdbcDao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import hcmuaf.nlp.core.DBConnect.DbConnector;
import hcmuaf.nlp.core.dao.QuestionTypeDao;
import hcmuaf.nlp.core.model.QuestionType;

public class QuestionTypeDaoImpl implements QuestionTypeDao {

	@Override
	public ArrayList<QuestionType> getTypeList() {
		ArrayList<QuestionType> typeList = new ArrayList<QuestionType>();
		Connection con = DbConnector.getConnection();
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

}
