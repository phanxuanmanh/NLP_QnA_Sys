package chukiso;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class DegitalSignature {
	public static void Sign(String fileName, String signaturFile,
			String privateKeybase64) throws NoSuchAlgorithmException,
			InvalidKeyException, InvalidKeySpecException, SignatureException,
			IOException {
		FileInputStream fis = new FileInputStream(new File(fileName));
		FileOutputStream fos = new FileOutputStream(new File(signaturFile));
		Signature sign = Signature.getInstance("MD5withRSA");
		byte[] keyByte = DatatypeConverter.parseBase64Binary(privateKeybase64);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		KeySpec privateKeySpec = new PKCS8EncodedKeySpec(keyByte);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
		sign.initSign(privateKey);
		byte[] buff = new byte[1024];
		int c = 0;
		while ((c = fis.read(buff)) == 1024) {
			sign.update(buff);
		}
		if (c > 0) {
			byte[] buff2 = new byte[c];
			System.arraycopy(buff, 0, buff2, 0, c);
			sign.update(buff2);
		}
		fis.close();
		byte[] sigByte = sign.sign();
		fos.write(sigByte, 0, sigByte.length);
		fos.flush();
		fos.close();

	}

	public static boolean verify(String fileName, String signaturFile,
			String publicKeyBase64) throws IOException,
			NoSuchAlgorithmException, InvalidKeySpecException,
			InvalidKeyException, SignatureException {
		FileInputStream fis = new FileInputStream(new File(fileName));
		DataInputStream dis = new DataInputStream(new FileInputStream(new File(
				signaturFile)));

		byte[] signByte = new byte[128];
		dis.read(signByte);
		dis.close();

		Signature sign = Signature.getInstance("MD5withRSA");

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		byte[] keyByte = DatatypeConverter.parseBase64Binary(publicKeyBase64);

		KeySpec publicKeySpec = new X509EncodedKeySpec(keyByte);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

		sign.initVerify(publicKey);
		byte[] buff = new byte[1024];
		int c = 0;
		while ((c = fis.read(buff)) == 1024) {
			sign.update(buff);
		}
		if (c > 0) {
			byte[] buff2 = new byte[c];
			System.arraycopy(buff, 0, buff2, 0, c);
			sign.update(buff2);
		}
		fis.close();

		return sign.verify(signByte);

	}

	public static void main(String[] args) throws NoSuchAlgorithmException,
			InvalidKeyException, InvalidKeySpecException, SignatureException,
			IOException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(1024);
		KeyPair pair = keyGen.genKeyPair();
		PublicKey pubKey = pair.getPublic();
		PrivateKey priKey = pair.getPrivate();
		String privateKeybase64 = DatatypeConverter.printBase64Binary(priKey
				.getEncoded());
		String publicKeyBase64 = DatatypeConverter.printBase64Binary(pubKey
				.getEncoded());
		System.out.println(publicKeyBase64);
//		Sign("test/a.txt", "test/sign", privateKeybase64);
//		boolean a = verify("test/a.txt", "test/sign", publicKeyBase64);
//		
//		System.out.println(a);
	}
}
