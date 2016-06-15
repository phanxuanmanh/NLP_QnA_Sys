package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.Base64;
import java.util.Date;

import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

import org.apache.axis2.AxisFault;

import sun.security.x509.X509CertImpl;

public class VerifyCert {
public static boolean verifyCer(X509Certificate cert){
	try {
		
		VerifyCertificateStub stub = new VerifyCertificateStub();
		VerifyCertificateStub.VerifyIncorp verifyIn=new  VerifyCertificateStub.VerifyIncorp();
		VerifyCertificateStub.VerifyPersom verifyPer= new VerifyCertificateStub.VerifyPersom();
		String publicKey = Base64.getEncoder().encodeToString(cert.getPublicKey().getEncoded());
		System.out.println(publicKey);
		verifyIn.setInfo(cert.getSubjectDN().toString());
		verifyPer.setInfo(cert.getSubjectDN().toString());
		verifyIn.setPublicKey(publicKey);
		verifyPer.setPublicKey(publicKey);
		VerifyCertificateStub.VerifyIncorpResponse res1= stub.verifyIncorp(verifyIn);
		VerifyCertificateStub.VerifyPersomResponse res2= stub.verifyPersom(verifyPer);
		if(res1.get_return()||res2.get_return())
			return true;
		
		
	} catch (AxisFault e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (VerifyCertificateNoSuchAlgorithmExceptionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (VerifyCertificateUnsupportedEncodingExceptionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return false;
}
public static void main(String[] args) {


}
}
