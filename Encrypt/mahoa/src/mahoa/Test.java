package mahoa;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

public class Test {

	public void testCipher() throws NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES", "BC");
		keyGen.init(128);
		Key key = keyGen.generateKey();
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] plaintText = "HEllo".getBytes();
		byte[] cipherText = cipher.doFinal(plaintText);
		String encryptText = new String(cipherText);
	}

}
