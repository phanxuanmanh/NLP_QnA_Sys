package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.Signature;
import java.security.cert.CertificateException;

import javax.security.cert.X509Certificate;

import com.sun.deploy.uitoolkit.impl.fx.ui.CertificateDialog.CertificateInfo;

import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;

public class Test {
	public static void main(String[] args) throws CertificateException {
//		try {
////			FileInputStream fis = new FileInputStream(new File(
////					"inbox/6836960072551865972.cer"));
////			X509Certificate cert = X509Certificate.getInstance(fis);
////			X509CertImpl c = new X509CertImpl(cert.getEncoded());
//		
//		} catch (FileNotFoundException
//				| javax.security.cert.CertificateException e) {
//			e.printStackTrace();
//		}
		File f = new File("tmp/4854838012910047850.cer");
		System.out.println(f.getName());
	}
}
