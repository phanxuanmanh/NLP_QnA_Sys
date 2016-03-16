package main.demo.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class WordAccessor {
	private static Connection con=null;

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

}
