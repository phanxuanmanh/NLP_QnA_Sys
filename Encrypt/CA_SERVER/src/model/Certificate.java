package model;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class Certificate {
	private X509Certificate cert;
	private PrivateKey privateKey;

	public Certificate(X509Certificate cert, PrivateKey privateKey) {
		super();
		this.cert = cert;
		this.privateKey = privateKey;
	}

	public X509Certificate getCert() {
		return cert;
	}

	public void setCert(X509Certificate cert) {
		this.cert = cert;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}
}
