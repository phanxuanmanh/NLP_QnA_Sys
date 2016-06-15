package mahoaDX;

import java.io.IOException;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DesEn {

	public static OutPut encrypt(String text) {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			Key key = keyGen.generateKey();
			byte[] plainBytes = text.getBytes("UTF-8");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] cipherBytes = cipher.doFinal(plainBytes);
			return new OutPut(base64Encode(cipherBytes),
					base64Encode(key.getEncoded()));

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decrypt(OutPut input) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKey key = new SecretKeySpec(base64Decode(input.password),
					"AES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] plainByte = cipher.doFinal(base64Decode(input.cipher));
			return new String(plainByte, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String base64Encode(byte[] input) {
		BASE64Encoder encode = new BASE64Encoder();
		return encode.encode(input);
	}

	public static byte[] base64Decode(String input) throws IOException {
		BASE64Decoder decode = new BASE64Decoder();
		return decode.decodeBuffer(input);
	}

	public static void main(String[] args) {
		OutPut key = encrypt("Hôm nay học chán quá");
		System.out.println(decrypt(key));
	}
}
