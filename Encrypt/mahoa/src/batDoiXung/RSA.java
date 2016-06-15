package batDoiXung;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.PrivateCredentialPermission;
import javax.xml.bind.DatatypeConverter;

public class RSA {

	public static KeyOutput keyGen() throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(1024);
		KeyPair pair = keyGen.genKeyPair();
		PublicKey pubKey = pair.getPublic();
		PrivateKey priKey = pair.getPrivate();
		KeyFactory kf = KeyFactory.getInstance("RSA");
		RSAPrivateKeySpec prikeySpec = kf.getKeySpec(priKey, RSAPrivateKeySpec.class);
		RSAPublicKeySpec pubKeySpec = kf.getKeySpec(pubKey, RSAPublicKeySpec.class);
		
		BigInteger modulus = prikeySpec.getModulus();//=pubkeySpec.getModulus();
		BigInteger publicExponent = pubKeySpec.getPublicExponent();
		BigInteger privateExponent = prikeySpec.getPrivateExponent();
		
		String moduHex = DatatypeConverter.printInteger(modulus);
		String pubEHex = DatatypeConverter.printInteger(publicExponent) ;
		String priEHex = DatatypeConverter.printInteger(privateExponent) ;
		return new KeyOutput(moduHex,priEHex, pubEHex);
	}

	public static byte[] encrypt(byte[] plain, PrivateKey priKey)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, priKey);// prikey
		byte[] cipherBytes = cipher.doFinal(plain);
		return cipherBytes;
	}
	public static byte[] encrypt(byte[] plain, String modulus, String priExponent)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
		RSAPrivateKeySpec privateKey = new RSAPrivateKeySpec(convertHex(modulus), convertHex(priExponent));
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey priKey = kf.generatePrivate(privateKey);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, priKey);// prikey
		byte[] cipherBytes = cipher.doFinal(plain);
		return cipherBytes;
	}
	public static byte[] decrypt(byte[] cipherBytes, String modulus, String pubExponent)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
		RSAPublicKeySpec publicKey = new RSAPublicKeySpec(convertHex(modulus), convertHex(pubExponent));
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PublicKey pubKey = kf.generatePublic(publicKey);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, pubKey);// prikey
		byte[] plainBytes = cipher.doFinal(cipherBytes);
		return plainBytes;
	}


	public static byte[] decrypt(byte[] cipherBytes, PublicKey pubKey)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, pubKey);
		byte[] plainBytes = cipher.doFinal(cipherBytes);
		return plainBytes;
	}

	public static void encrypt(String fileName, String encryptFile,
			PrivateKey key) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IOException,
			IllegalBlockSizeException, BadPaddingException {
		FileInputStream fis = new FileInputStream(new File(fileName));
		FileOutputStream fos = new FileOutputStream(new File(encryptFile));
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] buff = new byte[1024];
		int c = 0;
		while ((c = fis.read(buff)) == buff.length) {
			byte[] cipherBytes = cipher.update(buff);
			fos.write(cipherBytes);
			fos.flush();
		}
		if (c > 0) {
			byte[] tmp = new byte[c];
			System.arraycopy(buff, 0, tmp, 0, c);
			byte[] cipherBytes = cipher.doFinal(tmp);
			fos.write(cipherBytes);
			fos.flush();
		} else {
			cipher.doFinal();
		}

		fos.close();
		fis.close();

	}


	public static void decrypt(String fileName, String decryptFile,
			PublicKey key) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IOException,
			IllegalBlockSizeException, BadPaddingException {
		FileInputStream fis = new FileInputStream(new File(fileName));
		FileOutputStream fos = new FileOutputStream(new File(decryptFile));
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] buff = new byte[64];
		int c = 0;
		while ((c = fis.read(buff)) == buff.length) {
			byte[] cipherBytes = cipher.update(buff);
			fos.write(cipherBytes);
			System.out.println(c);
			fos.flush();
		}
		if (c > 0) {
			byte[] tmp = new byte[c];
			System.arraycopy(buff, 0, tmp, 0, c);
			byte[] cipherBytes = cipher.doFinal(tmp);
			fos.write(cipherBytes);
			fos.flush();
		} else {
			cipher.doFinal();
		}

		fos.close();
		fis.close();
	}
	public static BigInteger convertHex(String hex) {
	  BigInteger num = DatatypeConverter.parseInteger(hex);
		return num;
	}

	public static void main(String[] args) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, IOException, InvalidKeySpecException {
		KeyOutput key = keyGen();
		byte[] cipher = encrypt("Toi di hoc".getBytes(), key.getN(),key.getD());
		byte[] plain = decrypt(cipher, key.getN(),key.getE());
		System.out.println(new String(plain));
		RSAPrivateKeySpec privateKey = new RSAPrivateKeySpec(convertHex(key.getN()), convertHex(key.getD()));
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey priKey = kf.generatePrivate(privateKey);
		encrypt("test/b.txt","test/c.txt", priKey);
	}

}
