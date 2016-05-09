package hcmuaf.nlp.core.jdbcDao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import hcmuaf.nlp.core.DBConnect.DbConnector;
import hcmuaf.nlp.core.dao.AnswerDao;
import hcmuaf.nlp.core.dao.QnADao;
import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dto.QnAPair;

public class QnADaoImpl implements QnADao {

	@Override
	public boolean insertQnAPair(QnAPair pair) {
		QuestionDao questionDao = new QuestionDaoImpl();
		AnswerDao answerDao = new AnswerDaoImpl();
		Connection con = DbConnector.getConnection();
		int q_id = questionDao.insertQuestion(pair.getQuestion());
		int a_id = answerDao.insertAnswer(pair.getAnswer());
		if (q_id == 0 || a_id == 0) {
			System.out.println("inser error");
			return false;
		} else {

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

}
