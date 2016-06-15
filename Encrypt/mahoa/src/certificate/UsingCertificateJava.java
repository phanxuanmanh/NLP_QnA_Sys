package certificate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public class UsingCertificateJava {
public static void main(String[] args) throws CertificateException, IOException {
	CertificateFactory certFac = CertificateFactory.getInstance("x.509");
	FileInputStream fis = new FileInputStream("test.cer");
	Certificate cert = certFac.generateCertificate(fis);
	fis.close();
	System.out.println(cert);
	
}
}
