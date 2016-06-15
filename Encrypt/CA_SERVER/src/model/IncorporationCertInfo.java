package model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class IncorporationCertInfo {
private String companyName,address, bussinessLicense,phone,email,website, country;
private int zipcode;
public IncorporationCertInfo(String companyName, String address,
		String bussinessLicense, String phone, String email, String website,
		String country, int zipcode) {
	super();
	this.companyName = companyName;
	this.address = address;
	this.bussinessLicense = bussinessLicense;
	this.phone = phone;
	this.email = email;
	this.website = website;
	this.country = country;
	this.zipcode = zipcode;
}
public String getCompanyName() {
	return companyName;
}
public void setCompanyName(String companyName) {
	this.companyName = companyName;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getBussinessLicense() {
	return bussinessLicense;
}
public void setBussinessLicense(String bussinessLicense) {
	this.bussinessLicense = bussinessLicense;
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
public String getCountry() {
	return country;
}
public void setCountry(String country) {
	this.country = country;
}
public int getZipcode() {
	return zipcode;
}
public void setZipcode(int zipcode) {
	this.zipcode = zipcode;
}
public String getMD5() throws NoSuchAlgorithmException, UnsupportedEncodingException{
	MessageDigest md = MessageDigest.getInstance("MD5");
	String info = companyName+bussinessLicense+address+email+phone+website+zipcode+country;
	byte[] infoBytes = info.toUpperCase().getBytes("UTF-8");
	md.update(infoBytes);
	byte[] mdbytes = md.digest();
	
	return Base64.getEncoder().encodeToString(mdbytes);
}
}
