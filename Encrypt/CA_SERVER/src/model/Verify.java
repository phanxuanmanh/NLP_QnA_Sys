package model;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Verify {
	public boolean verifyPersom(PersonalCertInfo info, String publicKey) {

		PersonalCertInfo sourceInfo = CertificateDAO
				.getPersonalInfoByPublicKey(publicKey);
		try {
			if (info.getMD5().equals(sourceInfo.getMD5()))
				return true;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean verifyIncorp(IncorporationCertInfo info, String publicKey) {
		IncorporationCertInfo sourceInfo = CertificateDAO
				.getIncorpInfoByPublicKey(publicKey);
		try {
			if (info.getMD5().equals(sourceInfo.getMD5()))
				return true;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}
}
