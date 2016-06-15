package password_base_encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.dcm4che2.base64.Base64Decoder;
import org.dcm4che2.base64.Base64Encoder;

public class Salt {
	/*
	 * 1. random salt 2. generate Key from password using PBEKeySpec 3.
	 * AddPBEPasswordSpec(optional) 4. Create & Init Cipher for PBE 5.Encrypt &
	 * decrypt
	 */
	public static Key enCrypt(String plainText, String pwd)
			throws NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException {
		byte[] salt = new byte[8];
		Random rd = new Random();
		rd.nextBytes(salt);

		// String pwd = "abc123";
		PBEKeySpec keySpec = new PBEKeySpec(pwd.toCharArray());
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance("PBEWithMD5AndDES");
		SecretKey key = keyFactory.generateSecret(keySpec);
		int INTERATION = 1000;
		PBEParameterSpec parameter = new PBEParameterSpec(salt, INTERATION);
		Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
		cipher.init(Cipher.ENCRYPT_MODE, key, parameter);
		byte[] cipherText = cipher.doFinal(plainText.getBytes());

		return encode(cipherText, salt);

	}

	public static String decypt(Key cipherKey, String pwd)
			throws NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException {
		PBEKeySpec keySpec = new PBEKeySpec(pwd.toCharArray());
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance("PBEWithMD5AndDES");
		SecretKey key = keyFactory.generateSecret(keySpec);
		int INTERATION = 1000;

		byte[] salt = decode(cipherKey.getSalt());
		PBEParameterSpec parameter = new PBEParameterSpec(salt, INTERATION);
		Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
		cipher.init(Cipher.DECRYPT_MODE, key, parameter);
		byte[] plains = cipher.doFinal(decode(cipherKey.getCipher()));
		return new String(plains);

	}

	public static Key encode(byte[] cipher, byte[] salt)
			throws UnsupportedEncodingException {
		String cipherbase64 = new String(Base64Encoder.encode(cipher), "UTF-8");
		String saltBase64 = new String(Base64Encoder.encode(salt), "UTF-8");
		;
		return new Key(cipherbase64, saltBase64);
	}

	public static String encode(byte[] salt)
			throws UnsupportedEncodingException {
		return new String(Base64Encoder.encode(salt), "UTF-8");

	}

	public static byte[] decode(String text) {
		return Base64Decoder.decode(text);
	}

	public static void encrypt(String fileName, String fileDest, String pwd)
			throws IllegalBlockSizeException, BadPaddingException {

		try {
			FileInputStream fis = new FileInputStream(new File(fileName));
			FileOutputStream fos = new FileOutputStream(new File(fileDest));

			byte[] salt = new byte[8];
			Random rd = new Random();
			rd.nextBytes(salt);

			// String pwd = "abc123";
			PBEKeySpec keySpec = new PBEKeySpec(pwd.toCharArray());
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("PBEWithMD5AndDES");
			SecretKey key = keyFactory.generateSecret(keySpec);
			int INTERATION = 1000;
			PBEParameterSpec parameter = new PBEParameterSpec(salt, INTERATION);
			Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
			cipher.init(Cipher.ENCRYPT_MODE, key, parameter);

			byte[] buff = new byte[1024];
			int c = 0;
			fos.write(salt);
			while ((c = fis.read(buff)) == 1024) {
				byte[] out = cipher.update(buff);
				fos.write(out);
				fos.flush();
			}
			if (c > 0) {

				byte[] last = new byte[c];
				System.arraycopy(buff, 0, last, 0, c);
				byte[] out = cipher.doFinal(last);
				fos.write(out);
				fos.flush();
			}
			cipher.doFinal();
			fis.close();
			fos.close();

		} catch (InvalidKeyException | InvalidAlgorithmParameterException
				| NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeySpecException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void decrypt(String fileName, String fileDest, String pwd)
			throws IllegalBlockSizeException, BadPaddingException {

		try {
			FileInputStream fis = new FileInputStream(new File(fileName));
			FileOutputStream fos = new FileOutputStream(new File(fileDest));
			byte[] buff = new byte[1024];

			byte[] salt = new byte[8];
			fis.read(salt);

			// String pwd = "abc123";
			PBEKeySpec keySpec = new PBEKeySpec(pwd.toCharArray());
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("PBEWithMD5AndDES");
			SecretKey key = keyFactory.generateSecret(keySpec);
			int INTERATION = 1000;
			PBEParameterSpec parameter = new PBEParameterSpec(salt, INTERATION);
			Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
			cipher.init(Cipher.DECRYPT_MODE, key, parameter);
			int c;
			while ((c = fis.read(buff)) == 1024) {
				byte[] out = cipher.update(buff);
				fos.write(out);
				fos.flush();
			}

			if (c > 0) {

				byte[] last = new byte[c];
				System.arraycopy(buff, 0, last, 0, c);
				byte[] out = cipher.doFinal(last);
				fos.write(out);
				fos.flush();
			}

			fis.close();
			fos.close();

		} catch (InvalidKeyException | InvalidAlgorithmParameterException
				| NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeySpecException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String s = "hnay hoc an toan bao mat";
		String pwd = "rat buon";
		try {
//			Key k = enCrypt(s, "kdfnnrrk");
			Key c = new Key("kgjhlovbojko", "jfkvckghg");
//			System.out.println(k.toString());
			System.out.println(decypt(c,"sdryryt"));
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| InvalidKeySpecException | NoSuchPaddingException
				| InvalidAlgorithmParameterException
				| IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String fileName = "test/a.txt";
		String destName = "test/b.txt";
		try {
			encrypt(fileName, destName, "anh rat buon");
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		String src = "test/b.txt";
//		String dest = "test/c.txt";
//		try {
//			decrypt(src, dest, "anh rat buon");
//		} catch (IllegalBlockSizeException | BadPaddingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
