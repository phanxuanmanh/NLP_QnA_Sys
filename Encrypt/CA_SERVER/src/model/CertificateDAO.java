package model;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CertificateDAO {
	public static PersonalCertInfo getPersonalInfoByPublicKey(String publicKey) {
		PersonalCertInfo customer = null;
		try {
			Connection con = DBConnection.getConnection();
			String sql = "select * from customer where id=(select cusID from certinfo where publickey=?)";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, publicKey);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				String name = rs.getString(2);
				int birthYear = rs.getInt(3);
				String company = rs.getString(4);
				String address = rs.getString(5);
				String phone = rs.getString(6);
				String email = rs.getString(7);
				String website = rs.getString(8);
				String country = rs.getString(9);
				int zipCode = rs.getInt(10);
				customer = new PersonalCertInfo(name, birthYear, company, address,
						phone, email, website, country, zipCode);

			}
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;

	}
	public static IncorporationCertInfo getIncorpInfoByPublicKey(String publicKey){
		IncorporationCertInfo info = null;
		try {
			Connection con = DBConnection.getConnection();
			String sql = "select * from incorp where co_name=(select incorp_Name from certinfo where publickey=?)";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, publicKey);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				String name = rs.getString(1);
				String address = rs.getString(2);
				String bussinessLicense = rs.getString(3);
				String phone = rs.getString(4);
				String email = rs.getString(5);
				String website = rs.getString(6);
				String country = rs.getString(7);
				int zipCode = rs.getInt(8);
			info= new IncorporationCertInfo(name, address, bussinessLicense, phone, email, website, country, zipCode);

			}
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;

	}

	public int addPerson(PersonalCertInfo cus) {
		int id = 0;
		try {
			Connection con = DBConnection.getConnection();

			String sqlID = "select max(id) from customer";
			Statement st = con.createStatement();
			ResultSet rsID = st.executeQuery(sqlID);
			while (rsID.next())
				id = rsID.getInt(1);

			String sql = "insert into customer (id,name,birthyear,company,address,phone,email,website,country,zipcode) values * (?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement statement = con.prepareStatement(sql);

			statement.setInt(1, id);
			statement.setString(2, cus.getName());
			statement.setInt(3, cus.getBirthYear());
			statement.setString(4, cus.getCompany());
			statement.setString(5, cus.getAddress());
			statement.setString(6, cus.getPhone());
			statement.setString(7, cus.getEmail());
			statement.setString(8, cus.getWebsite());
			statement.setString(9, cus.getCoutry());
			statement.setInt(10, cus.getZipCode());
			statement.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}
	public boolean addIncorporation(IncorporationCertInfo info){
		try {
			Connection con = DBConnection.getConnection();
			String sql = "insert into incorp(co_name,address,bus_Licence_no,phone,email,website,country,zipcode) values * (?,?,?,?,?,?,?,?)";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, info.getCompanyName());
			statement.setString(2, info.getAddress());
			statement.setString(3, info.getBussinessLicense());
			statement.setString(4, info.getPhone());
			statement.setString(5, info.getEmail());
			statement.setString(6, info.getWebsite());
			statement.setString(7, info.getCountry());
			statement.setInt(8, info.getZipcode());
			statement.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	public void addPersonalCertificateInfo(CertificateInfo info,int customerID){
		Connection con = DBConnection.getConnection();
		try {
			String sql = "insert into cerinfo(serial,version,cusID,publickey,fromDay,expireDay) values(?,?,?,?,?,?);";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setObject(1,info.getSerial());
			statement.setString(2,info.getVersion());
			statement.setInt(3, customerID);
			statement.setString(4,info.getPublicKey());
			statement.setDate(5, new Date(info.getFromDay().getTime()));
			statement.setDate(6, new Date(info.getExpireDay().getTime()));
			statement.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	public void addIncorpCertificateInfo(CertificateInfo info,String corpName){
		Connection con = DBConnection.getConnection();
		try {
			String sql = "insert into cerinfo(serial,version,incorp_name,publickey,fromDay,expireDay) values(?,?,?,?,?,?);";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setObject(1,info.getSerial());
			statement.setString(2,info.getVersion());
			statement.setString(3, corpName);
			statement.setString(4,info.getPublicKey());
			statement.setDate(5, new Date(info.getFromDay().getTime()));
			statement.setDate(6, new Date(info.getExpireDay().getTime()));
			statement.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	public static void main(String[] args) {
		String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPkmr1qUKV7rKQsuEMkGIAcekzfQDTYDVQrhOPNmSj8hhG7K6ULnDo3aI8vy8tgkYvYYkjq3mbqPEIRmrgex/jMeqsDlzHQQsDNN/aWy/e+ShNDJHp9NqFgnpRGVIhSUeMbFgcNHC9C6f2PEmO3uZv2ZGREUzqn/WZbU4LNBZ+MwIDAQAB";
		System.out.println(getIncorpInfoByPublicKey(key).getCompanyName());
	}
}
