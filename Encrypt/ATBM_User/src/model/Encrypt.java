package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {
public static String EncryptFile(PrivateKey prikey,String fileName){
	String password=new String();
	try {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
		Key key = keyGen.generateKey();
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] buff = new byte[1024];
		File fileIn= new File(fileName);
		FileInputStream fis=new FileInputStream(fileIn);
		FileOutputStream fos= new FileOutputStream(new File("tmp/" +fileIn.getName()));
		int c=0;
		while ((c = fis.read(buff)) == 1024) {
			byte[] out=cipher.update(buff);
			fos.write(out);
			fos.flush();
		}
		if (c > 0) {
			byte[] last = new byte[c];
			System.arraycopy(buff, 0, last, 0, c);
			byte[] out =cipher.doFinal(last);
			fos.write(out);
			fos.flush();
		}
		
		cipher.doFinal();
		fis.close();fos.close();
		
		Cipher passcipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		passcipher.init(Cipher.ENCRYPT_MODE, prikey);
		byte[] cipherBytes = passcipher.doFinal(key.getEncoded());
		password= Base64.getEncoder().encodeToString(cipherBytes);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return password;
}
public static void decryptFile(String fileName,PublicKey pubKey,String password,String outPutFolder){
	try {
		File infile =new File(fileName);
		FileInputStream fis = new FileInputStream(infile);
		FileOutputStream fos = new FileOutputStream(new File(outPutFolder)+"/"+infile.getName());
		Cipher passcipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		passcipher.init(Cipher.DECRYPT_MODE, pubKey);// prikey
		byte[] passBytes = passcipher.doFinal(Base64.getDecoder().decode(password.getBytes("UTF-8")));
		SecretKey key = new SecretKeySpec(passBytes,
				"AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] buff = new byte[1024];
		int c=0;
		while ((c = fis.read(buff)) == 1024) {
			byte[] out=cipher.update(buff);
			fos.write(out);
			fos.flush();
		}
		if (c > 0) {
			byte[] last = new byte[c];
			System.arraycopy(buff, 0, last, 0, c);
			byte[] out =cipher.doFinal(last);
			fos.write(out);
			fos.flush();
		}
		
		cipher.doFinal();
		fis.close();fos.close();
		
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IllegalBlockSizeException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (BadPaddingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InvalidKeyException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (NoSuchPaddingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

}
