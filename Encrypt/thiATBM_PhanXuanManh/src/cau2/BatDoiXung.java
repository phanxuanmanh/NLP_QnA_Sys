package cau2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class BatDoiXung {

	public static String encrypt(String fileName, String encyptFile,
			String privateKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			KeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder()
					.decode(privateKey));
			PrivateKey key = keyFactory.generatePrivate(keySpec);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);

			FileInputStream fis = new FileInputStream(new File(fileName));
			FileOutputStream fos = new FileOutputStream(new File(encyptFile));

			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			Key enkey = keyGen.generateKey();
			Cipher cipher2 = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher2.init(Cipher.ENCRYPT_MODE, enkey);
			byte[] buff = new byte[1024];

			int c;
			while ((c = fis.read(buff)) == 1024) {
				fos.write(cipher2.update(buff, 0, c));
				fos.flush();
			}
			if (c > 0) {
				byte[] tmp = new byte[c];
				System.arraycopy(buff, 0, tmp, 0, c);
				byte[] cipherBytes = cipher2.doFinal(tmp);
				fos.write(cipherBytes);
				fos.flush();
			} else {
				cipher2.doFinal();
			}
			

			fis.close();

			fos.close();

			return Base64.getEncoder().encodeToString(
					cipher.doFinal(enkey.getEncoded()));

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void decrypt(String fileName, String decryptFile,
			String publicKey, String enkeyText) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			KeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder()
					.decode(publicKey.getBytes()));
			PublicKey key = keyFactory.generatePublic(publicKeySpec);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] keyBytes = cipher.doFinal(Base64.getDecoder().decode(
					enkeyText.getBytes("UTF-8")));

			Cipher cipher2 = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKey enkey = new SecretKeySpec(keyBytes,
					"AES");
			cipher2.init(Cipher.DECRYPT_MODE, enkey);
			
			
			FileInputStream fis = new FileInputStream(new File(fileName));
			FileOutputStream fos = new FileOutputStream(new File(decryptFile));
			byte[] buff = new byte[1024];
			int c;
			while ((c = fis.read(buff)) == buff.length) {
				fos.write(cipher2.update(buff, 0, c));
				fos.flush();
			}
			if (c > 0) {
				byte[] tmp = new byte[c];
				System.arraycopy(buff, 0, tmp, 0, c);
				byte[] cipherBytes = cipher2.doFinal(tmp);
				fos.write(cipherBytes);
				fos.flush();
			} else {
				cipher2.doFinal();
			}

		

			fis.close();

			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(1024);
			KeyPair keyPair = keyGen.genKeyPair();
			String publicKey = Base64.getEncoder().encodeToString(
					keyPair.getPublic().getEncoded());
			String privateKey = Base64.getEncoder().encodeToString(
					keyPair.getPrivate().getEncoded());

			String key = encrypt("test/a.pdf", "test/b.pdf", privateKey);
			
			decrypt("test/b.pdf", "test/c.pdf", publicKey, key);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
