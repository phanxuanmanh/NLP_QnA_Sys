package mahoa;

import java.security.Key;

public class KeyOut {
	private String keyOut;
	private String encryptText;
	/**
	 * @param keyOut
	 * @param encryptText
	 */
	public KeyOut(String keyOut, String encryptText) {
		super();
		this.keyOut = keyOut;
		this.encryptText = encryptText;
	}
	/**
	 * @return the keyOut
	 */
	public String getKeyOut() {
		return keyOut;
	}
	/**
	 * @param keyOut the keyOut to set
	 */
	public void setKeyOut(String keyOut) {
		this.keyOut = keyOut;
	}
	/**
	 * @return the encryptText
	 */
	public String getEncryptText() {
		return encryptText;
	}
	/**
	 * @param encryptText the encryptText to set
	 */
	public void setEncryptText(String encryptText) {
		this.encryptText = encryptText;
	}
	
	


}
