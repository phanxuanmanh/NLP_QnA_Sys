package model;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class VerifyCertificate {
	public static boolean verifyPersom(String info, String publicKey)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		PersonalCertInfo sourceInfo = CertificateDAO
				.getPersonalInfoByPublicKey(publicKey);
		if (sourceInfo.MD5().equals(HashMd5.getMD5(info))){
			System.out.println("complete");
			return true;
			}else{
		return false;
	}}

	public static boolean verifyIncorp(String info, String publicKey)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		IncorporationCertInfo sourceInfo = CertificateDAO
				.getIncorpInfoByPublicKey(publicKey);
		if (sourceInfo == null)
			return false;
		if (HashMd5.getMD5(sourceInfo.MD5()).equals(HashMd5.getMD5(info)))
			return true;
		return false;
	}

}
