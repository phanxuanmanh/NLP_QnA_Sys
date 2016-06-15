package model;

import java.math.BigInteger;
import java.util.Date;

public class CertificateInfo {
private BigInteger serial;
private String version;
private PersonalCertInfo customer;
private String publicKey;
private Date fromDay,expireDay;
public CertificateInfo(BigInteger serial, String version, PersonalCertInfo customer,
		String publicKey, Date fromDay, Date expireDay) {
	this.serial = serial;
	this.version = version;
	this.customer = customer;
	this.publicKey = publicKey;
	this.fromDay = fromDay;
	this.expireDay = expireDay;
}
public BigInteger getSerial() {
	return serial;
}
public void setSerial(BigInteger serial) {
	this.serial = serial;
}
public String getVersion() {
	return version;
}
public void setVersion(String version) {
	this.version = version;
}
public PersonalCertInfo getCustomer() {
	return customer;
}
public void setCustomer(PersonalCertInfo customer) {
	this.customer = customer;
}
public String getPublicKey() {
	return publicKey;
}
public void setPublicKey(String publicKey) {
	this.publicKey = publicKey;
}
public Date getFromDay() {
	return fromDay;
}
public void setFromDay(Date fromDay) {
	this.fromDay = fromDay;
}
public Date getExpireDay() {
	return expireDay;
}
public void setExpireDay(Date expireDay) {
	this.expireDay = expireDay;
}

}
