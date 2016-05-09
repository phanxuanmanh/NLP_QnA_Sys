package hcmuaf.nlp.core.jdbcDao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import hcmuaf.nlp.core.DBConnect.WikiDbConnector;
import hcmuaf.nlp.core.dao.WikiConceptDao;

public class WikiConceptDaoImpl implements WikiConceptDao {

	@Override
	public ArrayList<Integer> getConCeptList() {
		ArrayList<Integer> listID = new ArrayList<>();
		Connection con = WikiDbConnector.getConnection();
		try {
			PreparedStatement pp = con
					.prepareStatement("SELECT page_latest FROM wikidb.page  where page_len>200");
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
	public String getConceptText(int page_latest) {
		String question = null;
		Connection con = WikiDbConnector.getConnection();
		try {
			PreparedStatement pp = con
					.prepareStatement("SELECT cast(old_text as char(600000) character set utf8) FROM wikidb.text  where old_id =?");
			pp.setInt(1, page_latest);
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
	public void updateWordCount(int page_latest, int wordId, int freq) {
		Connection con = WikiDbConnector.getConnection();
		try {
			PreparedStatement pp = con
					.prepareStatement("select * from  concepts_words where page_id =? and word_id = ?");
			pp.setInt(1, page_latest);
			pp.setInt(2, wordId);
			ResultSet result = pp.executeQuery();
			if (result.next()) {
				PreparedStatement pp3 = con
						.prepareStatement("update concepts_words set freq =freq+ 1  where page_id =? and word_id = ? and freq = ?");
				pp3.setInt(1, page_latest);
				pp3.setInt(2, wordId);
				pp3.setInt(3, freq);
				pp3.execute();
				pp3.close();
			} else {
				PreparedStatement pp2 = con
						.prepareStatement("insert into concepts_words(page_id,word_id,freq) values (?,?,?)");
				pp2.setInt(1, page_latest);
				pp2.setInt(2, wordId);
				pp2.setInt(3, freq);
				pp2.execute();
				pp2.close();
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

}
