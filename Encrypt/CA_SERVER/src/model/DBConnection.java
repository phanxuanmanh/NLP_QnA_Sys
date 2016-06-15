package model;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection getConnection(){
		Connection con =null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url ="jdbc:mysql://127.0.0.1/ca";
			con= DriverManager.getConnection(url, "root", "manh980838");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	public static void main(String[] args) {
		System.out.println(getConnection());
	}
}
