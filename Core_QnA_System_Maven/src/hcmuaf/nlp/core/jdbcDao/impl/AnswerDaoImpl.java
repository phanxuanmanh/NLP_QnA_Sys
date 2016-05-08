package hcmuaf.nlp.core.jdbcDao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hcmuaf.nlp.core.DBConnect.DbConnector;
import hcmuaf.nlp.core.dao.AnswerDao;

public class AnswerDaoImpl implements AnswerDao {

	@Override
	public int insertAnswer(String answer) {
		Connection con = DbConnector.getConnection();
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

}
