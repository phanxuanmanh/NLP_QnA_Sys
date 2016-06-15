package mahoa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class GiaiMa {
	public static KeyOut encrypt(String text) {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			Key key = keyGen.generateKey();
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] plainText = text.getBytes("UTF-8");
			byte[] cipherText = cipher.doFinal(plainText);
			String hex = DatatypeConverter.printHexBinary(cipherText);
			return new KeyOut(storeKey(key), hex);

		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String decrypt(String encryptText, String keyHex) {

		try {

			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, restoreKey(keyHex));
			byte[] textbytes = DatatypeConverter.parseHexBinary(encryptText);
			byte[] plainText = cipher.doFinal(textbytes);
			return new String(plainText, "UTF-8");

		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException | UnsupportedEncodingException e) {

			e.printStackTrace();
			return null;
		}

	}

	public static String storeKey(Key key) {
		byte[] bytes = key.getEncoded();
		String hex = DatatypeConverter.printHexBinary(bytes);
		return hex;
	}

	public static Key restoreKey(String hex) {
		byte[] keybytes = DatatypeConverter.parseHexBinary(hex);
		SecretKey myKey = new SecretKeySpec(keybytes, "AES");
		return myKey;
	}

	public static void encryptFile(String source, String dest, String hexKey)
			throws IOException,  BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException {
		File in = new File(source);
		File out = new File(dest);
		FileInputStream input = new FileInputStream(in);
		FileOutputStream output = new FileOutputStream(out);
		byte[] inbytes = new byte[1024];

		int c;
		Key key = restoreKey(hexKey);
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		while ((c = input.read(inbytes)) ==1024) {
			 byte[] encrypt = cipher.doFinal(inbytes);
			 output.write(encrypt);
		}
		if(c<0&&c<1024){
			
		}

	}

	public static void decryptFile() {

	}

	public static void main(String[] args) {
		KeyOut out = encrypt("I'm Free");
		System.out.println(out.getKeyOut());
		System.out.println(out.getEncryptText());

		System.out.println(decrypt(out.getEncryptText(), out.getKeyOut()));
	}
}
