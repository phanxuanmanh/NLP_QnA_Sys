package signature;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.xml.bind.DatatypeConverter;

public class DGSignature {
public static void sign(String filename,String sigFile,String privateKey){

	try {
		PrintWriter pw = new PrintWriter(new FileOutputStream(new File(sigFile)));
		FileInputStream fis = new FileInputStream(new File(filename));
		Signature sig = Signature.getInstance("MD5WithRSA");
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		KeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey.getBytes()));
		PrivateKey key = keyFactory.generatePrivate(privateKeySpec);
		sig.initSign(key);
		byte[] buff = new byte[1024];
		int c =0;
		while((c =fis.read(buff))>0){
			sig.update(buff, 0, c);
		}
		fis.close();
		byte[] signByte = sig.sign();
		pw.write(Base64.getEncoder().encodeToString(signByte));
		pw.flush();
		pw.close();
	} catch (Exception e) {
		e.printStackTrace();
	} 
}
public static boolean verify(String fileName,String sigFile, String publicKey){
	try {
		KeyFactory keyfactory = KeyFactory.getInstance("RSA");
		KeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey.getBytes()));
		PublicKey key = keyfactory.generatePublic(publicKeySpec);
		BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(new File(sigFile))));
		FileInputStream fis = new FileInputStream(new File(fileName));
		byte[] signByte= Base64.getDecoder().decode(bf.readLine().getBytes());
		bf.close();
		Signature sign = Signature.getInstance("MD5WithRSA");
		sign.initVerify(key);
		byte[] buff = new byte[1024];
		int c;
		while((c=fis.read(buff))>0){
			sign.update(buff, 0, c);
		}
		fis.close();
		return sign.verify(signByte);
		
		
	} catch (Exception e) {
		e.printStackTrace();
		return false;
	}
}
public static void main(String[] args) {
	try {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(1024);
		KeyPair pair = keyGen.genKeyPair();
		PublicKey pubKey = pair.getPublic();
		PrivateKey priKey = pair.getPrivate();
		String privateKeybase64 = DatatypeConverter.printBase64Binary(priKey
				.getEncoded());
		String publicKeyBase64 = DatatypeConverter.printBase64Binary(pubKey
				.getEncoded());
		sign("test/b.txt", "test/sign", privateKeybase64);
		boolean a = verify("test/b.txt", "test/sign", publicKeyBase64);
		
		System.out.println(a);
		
	} catch (Exception e) {
		
	}
}
}
