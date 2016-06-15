package password_base_encryption;


public class Key {
private String cipher, salt;

public Key(String cipher, String salt) {

	this.cipher = cipher;
	this.salt = salt;
}
@Override
	public String toString() {
		
		return this.cipher +"\n" + this.salt;
	}
public String getCipher() {
	return cipher;
}
public String getSalt() {
	return salt;
}

}
