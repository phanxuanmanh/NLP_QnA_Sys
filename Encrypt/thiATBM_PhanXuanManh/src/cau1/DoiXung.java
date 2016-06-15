package cau1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DoiXung {
	byte[] iv = { 0, 1, 0, 1, 1, 0, 0, 1, 1, 1 };

	public static String Enrypt(String plain, String keyFile, byte[] iv) {
		try {
			PrintWriter pr = new PrintWriter(new FileOutputStream(new File(
					"test/key")));

			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			SecretKey key = keyGen.generateKey();
			pr.write(Base64.getEncoder().encodeToString(key.getEncoded()));
			pr.flush();
			pr.close();
			IvParameterSpec ivKeySpec = new IvParameterSpec(iv);
			byte[] plainBytes = plain.getBytes("UTF-8");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, ivKeySpec);
			byte[] cipherBytes = cipher.doFinal(plainBytes);
			return Base64.getEncoder().encodeToString(cipherBytes);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decrypt(String cipherText, String keyFile, byte[] iv) {
		try {

			BufferedReader bf = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(keyFile))));
			byte[] keyBytes = Base64.getDecoder().decode(
					bf.readLine().getBytes());
			bf.close();
			SecretKey key = new SecretKeySpec(keyBytes, "AES");
			IvParameterSpec ivKeySpec = new IvParameterSpec(iv);
			byte[] cipherBytes = Base64.getDecoder().decode(cipherText.getBytes("UTF-8"));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, ivKeySpec);
			byte[] plainBytes = cipher.doFinal(cipherBytes);

			return new String(plainBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
     byte[] iv = "1234567812345678".getBytes();
     String s = "hôm nay thi an toàn bảo mật thật là vui";
     String cipher =Enrypt(s,"test/key", iv);
     System.out.println(decrypt(cipher, "test/key", iv));
     
	}
}
