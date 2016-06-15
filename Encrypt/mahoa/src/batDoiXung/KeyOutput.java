package batDoiXung;

import java.security.PrivateKey;
import java.security.PublicKey;

public class KeyOutput {
private String n;
 private String e;
 private String d;
public KeyOutput(String n, String e, String d) {
	this.n = n;
	this.e = e;
	this.d = d;
}
public String getN() {
	return n;
}
public void setN(String n) {
	this.n = n;
}
public String getE() {
	return e;
}
public void setE(String e) {
	this.e = e;
}
public String getD() {
	return d;
}
public void setD(String d) {
	this.d = d;
}




}
