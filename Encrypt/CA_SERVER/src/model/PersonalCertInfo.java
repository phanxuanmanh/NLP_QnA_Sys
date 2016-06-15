package model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PersonalCertInfo {
private String name;
private int birthYear;
private String company,address,phone,email,website,coutry;
private int  zipCode;
public PersonalCertInfo(String name, int birthYear, String company, String address,
		String phone, String email, String website, String coutry, int zipCode) {
	this.name = name;
	this.birthYear = birthYear;
	this.company = company;
	this.address = address;
	this.phone = phone;
	this.email = email;
	this.website = website;
	this.coutry = coutry;
	this.zipCode = zipCode;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getBirthYear() {
	return birthYear;
}
public void setBirthYear(int birthYear) {
	this.birthYear = birthYear;
}
public String getCompany() {
	return company;
}
public void setCompany(String company) {
	this.company = company;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getWebsite() {
	return website;
}
public void setWebsite(String website) {
	this.website = website;
}
public String getCoutry() {
	return coutry;
}
public void setCoutry(String coutry) {
	this.coutry = coutry;
}
public int getZipCode() {
	return zipCode;
}
public void setZipCode(int zipCode) {
	this.zipCode = zipCode;
}
public String getMD5() throws NoSuchAlgorithmException, UnsupportedEncodingException{
	MessageDigest md = MessageDigest.getInstance("MD5");
	String info = name + birthYear+company+address+coutry+email+phone+website+zipCode;
	byte[] infoBytes = info.toUpperCase().getBytes("UTF-8");
	md.update(infoBytes);
	byte[] mdbytes = md.digest();
	
	return Base64.getEncoder().encodeToString(mdbytes);
}
}
