package certificate;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

import javax.xml.bind.DatatypeConverter;

public class Signature {
private KeyPair keyPair =null;
public String sign(String data) throws Exception{
	KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
	kpg.initialize(1024);
	byte[] dataBytes= data.getBytes("UTF-8");
	
	java.security.Signature sign =java.security.Signature.getInstance("MD5WithRSA");
	sign.initSign(keyPair.getPrivate());
	
	sign.update(dataBytes);
	return DatatypeConverter.printBase64Binary(dataBytes);
}
}
