package block;

import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import sun.misc.BASE64Encoder;

import com.sun.org.apache.bcel.internal.generic.F2D;

public class Salt {

	public static KeyOut encrypt(String text, String pass) {
		try {
			byte[] salt = new byte[8];
			Random rd = new Random();
			rd.nextBytes(salt);
			PBEKeySpec keySpec = new PBEKeySpec(pass.toCharArray());
			SecretKeyFactory keyFactory =SecretKeyFactory.getInstance("PBEWithMD5AndDES");
			SecretKey key =keyFactory.generateSecret(keySpec);
			int INTERATION =1000;
			PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, INTERATION);
			Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
			cipher.init(Cipher.ENCRYPT_MODE, key,parameterSpec);
			byte[] cipherByte = cipher.doFinal(text.getBytes("UTF8"));
		   return new KeyOut(Base64.getEncoder().encodeToString(cipherByte), Base64.getEncoder().encodeToString(salt));
		   
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static  String decrypt(KeyOut keyIn, String pass){
		try {
			PBEKeySpec keySpec = new PBEKeySpec(pass.toCharArray());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
			SecretKey key = keyfactory.generateSecret(keySpec);
			int ITERATION = 1000;
			PBEParameterSpec parameterSPEC = new PBEParameterSpec(Base64.getDecoder().decode(keyIn.salt),ITERATION);
			Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
			cipher.init(Cipher.DECRYPT_MODE,key,parameterSPEC);
			byte[] plainBytes = cipher.doFinal(Base64.getDecoder().decode(keyIn.cipher));
			return new String(plainBytes,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace(); return null;
		}
	}
	public static void main(String[] args) {
		KeyOut out = encrypt("Hôm nay thi an toàn bảo mật lúc 7h tối", "hakhkahzkhzkhcz");
		System.out.println(decrypt(out, "hakhkahzkhzkhcz"));
	}
}
