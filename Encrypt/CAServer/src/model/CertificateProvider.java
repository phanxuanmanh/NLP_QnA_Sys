package model;

import sun.security.x509.*;

import java.security.*;
import java.math.BigInteger;
import java.util.Base64;
import java.util.Date;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class CertificateProvider {
	public X509CertImpl generateCertificate(String dn, KeyPair pair, Date from,
			Date to, String algorithm) throws GeneralSecurityException,
			IOException {
		PrivateKey privkey = pair.getPrivate();
		X509CertInfo info = new X509CertInfo();
		CertificateValidity interval = new CertificateValidity(from, to);
		BigInteger sn = new BigInteger(64, new SecureRandom());
		X500Name sub = new X500Name(dn);
		X500Name owner = new X500Name("CN=XuanManh");
		info.set(X509CertInfo.VALIDITY, interval);
		info.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(sn));
		info.set(X509CertInfo.SUBJECT, sub);
		info.set(X509CertInfo.ISSUER, owner);
		info.set(X509CertInfo.KEY, new CertificateX509Key(pair.getPublic()));
		info.set(X509CertInfo.VERSION, new CertificateVersion(
				CertificateVersion.V3));
		AlgorithmId algo = new AlgorithmId(AlgorithmId.md5WithRSAEncryption_oid);
		info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(algo));

		X509CertImpl cert = new X509CertImpl(info);
		cert.sign(privkey, algorithm);
		// Update the algorith, and resign.
		algo = (AlgorithmId) cert.get(X509CertImpl.SIG_ALG);
		info.set(CertificateAlgorithmId.NAME + "."
				+ CertificateAlgorithmId.ALGORITHM, algo);
		cert = new X509CertImpl(info);
		cert.sign(privkey, algorithm);
		return cert;
	}

	public static void createPersonalCert( PersonalCertInfo person,
			String folderName, Date from, Date to)
			throws NoSuchAlgorithmException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(1024);
		KeyPair pair = keyGen.genKeyPair();
		X509CertImpl cert;
		try {
			CertificateProvider pv = new CertificateProvider();
			String dn="CN="+person.getName()+",O="+person.getCompany()+",ST="+person.getAddress()
					+",C="+person.getCoutry()+",L="+person.getZipCode()+",OU="+person.getEmail();
			cert = pv.generateCertificate(dn, pair, from, to, "MD5WithRSA");
			int id = CertificateDAO.addPerson(person);
			CertificateInfo info = new CertificateInfo(cert.getSerialNumber(),
					cert.getVersion() + "", person, java.util.Base64
							.getEncoder().encodeToString(
									cert.getPublicKey().getEncoded()), from, to);
			CertificateDAO.addPersonalCertificateInfo(info, id);
			FileOutputStream fos = new FileOutputStream(folderName + "//"
					+ cert.getSerialNumber() + ".cer");
			fos.write("-----BEGIN CERTIFICATE-----\n".getBytes("US-ASCII"));
			System.out.println(cert.toString());
			fos.write(Base64.getEncoder().encode(cert.getEncoded()));
			fos.write("-----END CERTIFICATE-----\n".getBytes("US-ASCII"));
			fos.flush();
			fos.close();
			PrintWriter wt = new PrintWriter(new File(folderName + "//"
					+ cert.getSerialNumber() + ".dat"));
			wt.write(Base64.getEncoder().encodeToString(
					pair.getPrivate().getEncoded()));
			wt.flush();
			wt.close();

		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void createIncorpCert(
			IncorporationCertInfo incorp, String folderName, Date from, Date to)
			throws NoSuchAlgorithmException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(1024);
		KeyPair pair = keyGen.genKeyPair();
		X509CertImpl cert;
		try {
			CertificateProvider pv = new CertificateProvider();
			String dn="CN="+incorp.getCompanyName()+",O="+incorp.getCompanyName()+",ST="+incorp.getAddress()
					+",C="+incorp.getCountry()+",L="+incorp.getZipcode()+",OU="+incorp.getWebsite()+"/"+incorp.getEmail();
			cert = pv.generateCertificate(dn, pair, from, to, "MD5WithRSA");
			CertificateInfo info = new CertificateInfo(cert.getSerialNumber(),
					cert.getVersion() + "", null, java.util.Base64.getEncoder()
							.encodeToString(cert.getPublicKey().getEncoded()),
					from, to);
			
			CertificateDAO.addIncorporation(incorp);
			CertificateDAO.addIncorpCertificateInfo(info,
					incorp.getCompanyName());
			FileOutputStream fos = new FileOutputStream(folderName + "//"
					+ cert.getSerialNumber() + ".cer");
			fos.write("-----BEGIN CERTIFICATE-----\n".getBytes("US-ASCII"));
			System.out.println(cert.toString());
			fos.write(Base64.getEncoder().encode(cert.getEncoded()));
			fos.write("-----END CERTIFICATE-----\n".getBytes("US-ASCII"));
			fos.flush();
			fos.close();
			PrintWriter wt = new PrintWriter(new File(folderName + "//"
					+ cert.getSerialNumber() + ".dat"));
			wt.write(Base64.getEncoder().encodeToString(
					pair.getPrivate().getEncoded()));
			wt.flush();
			wt.close();

		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
