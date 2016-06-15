package mahoaBDX;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import com.sun.swing.internal.plaf.basic.resources.basic;

import sun.security.pkcs.PKCS8Key;

public class Asymatric {
	public static String encrypt(String text, String privateKey){
	try {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		KeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
		PrivateKey key = keyFactory.generatePrivate(keySpec);
		Cipher cipher= Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE,key);
		byte[] cipherBytes = cipher.doFinal(text.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(cipherBytes);
		 
	} catch (Exception e) {
		e.printStackTrace();return null;
	}
		
	}
	public static String decrypt(String cipherText,String publicKey){
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			KeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey.getBytes()));
			PublicKey key = keyFactory.generatePublic(publicKeySpec);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] plainBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText.getBytes("UTF-8")));
			return new String(plainBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	public static void main(String[] args)  {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(1024);
			KeyPair keyPair= keyGen.genKeyPair();
			String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
			String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
			
			String cipher = encrypt("chieu nay thi an toan bm", privateKey);
			System.out.println(decrypt(cipher, publicKey));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
