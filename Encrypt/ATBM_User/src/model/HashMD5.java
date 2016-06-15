package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class HashMD5 {
	public static byte[] getMD5(String file) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			FileInputStream fis = new FileInputStream(new File(file));
			byte[] buff = new byte[1024];
			int c = 0;
			while ((c = fis.read(buff)) > 0) {
				md.update(buff, 0, c);
			}
			fis.close();

			return md.digest();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String encryptMD5(String file, PrivateKey priKey) {
		String enMD5 = new String();
		try {
			Cipher passcipher;
			passcipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			passcipher.init(Cipher.ENCRYPT_MODE, priKey);
			byte[] cipherBytes = passcipher.doFinal(getMD5(file));
			enMD5 = Base64.getEncoder().encodeToString(cipherBytes);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
		}
		return enMD5;
	}

	public static byte[] decryptMD5(String enMd5, PublicKey pubKey) {
		byte[] md5 = null;
		try {
			byte[] deMd5= Base64.getDecoder().decode(enMd5.getBytes());
			Cipher passcipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			passcipher.init(Cipher.DECRYPT_MODE, pubKey);
			md5 = passcipher.doFinal(deMd5);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
		}
		return md5;
	}

	public static boolean isEdited(String file, String md5Text, PublicKey pubKey) {
		try {
		 String md5 =Base64.getEncoder().encodeToString( getMD5(file));
		 String oldMd5 = Base64.getEncoder().encodeToString(decryptMD5(md5Text, pubKey));
		 return !md5.equals(oldMd5);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(1024);
		KeyPair keyPair = keyGen.genKeyPair();
		String md5 = encryptMD5("inbox/TD 1.pdf", keyPair.getPrivate());
		System.out.println(isEdited("inbox/TD 2.pdf", md5, keyPair.getPublic()));
	}
}
