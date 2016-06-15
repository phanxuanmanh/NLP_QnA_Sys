package model;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.swing.JOptionPane;


public class SendFile implements Runnable {
	public static int SERVER_PORT = 1234;
	static Socket socket;
	DataOutputStream dos;
	DataInputStream dis;
	String filePath,address,cerFilePath,privateKeyPath,password;
	
	
	public SendFile(String filePath, String address, String cerFilePath,
			String privateKeyPath) {
		super();
		this.filePath = filePath;
		this.address = address;
		this.cerFilePath = cerFilePath;
		this.privateKeyPath = privateKeyPath;
	}
	public PrivateKey getprivateKey(){
		PrivateKey prikey=null;
		String privateKey;
		try {
			BufferedReader read = new BufferedReader(new InputStreamReader(
					new FileInputStream(privateKeyPath)));
			privateKey = read.readLine();
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			KeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder()
					.decode(privateKey));
			prikey = keyFactory.generatePrivate(keySpec);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Please import your private key file");
		}
		return prikey;
	}
	public  void getConnection() {
		try {
			socket= new Socket(address,SERVER_PORT);
			dis=new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendCertificate(){
		getConnection();
		if(socket!=null){
			try {
				File sfile= new File(cerFilePath);
				dos.writeLong(sfile.length());
				dos.flush();
				FileInputStream fis = new FileInputStream(sfile);
				byte[] signByte =new byte[(int) sfile.length()];
				fis.read(signByte);
				dos.write(signByte);
				dos.flush();
				fis.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}catch (NullPointerException e) {
				JOptionPane.showMessageDialog(null, "please import your Certificate file");
			}
			
		}
	}
	public void sendMD5() throws IOException{
		while(true){
			if(dis.readInt()==2){
				String md5 =HashMD5.encryptMD5(filePath, getprivateKey());
				dos.writeUTF(md5);
				dos.flush();
				break;
			}
		}
	}
	public void encrypt(){
		password = Encrypt.EncryptFile(getprivateKey(), filePath);
	}
	public void sendFile() throws IOException{
		File inFile = new File(filePath);
		File  enFile = new File("tmp/"+inFile.getName());
		while(true){
			if(dis.readInt()==3){
				dos.writeUTF(password);
				dos.flush();
			}
			if(dis.readInt()==4){
				dos.writeUTF(inFile.getName());
				dos.flush();
				dos.writeLong(enFile.length());
				dos.flush();
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(enFile));
				byte[] buffer = new byte[1024];
				int i;
				while ((i = bis.read(buffer)) != -1) {
					dos.write(buffer, 0, i);
					dos.flush();
				}
				bis.close();
				enFile.delete();
				break;
			}
		}
	}
	
	
	public void run() {
			sendCertificate();
			try {
				sendMD5();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				sendFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCerFilePath() {
		return cerFilePath;
	}
	public void setCerFilePath(String cerFilePath) {
		this.cerFilePath = cerFilePath;
	}
	public String getPrivateKeyPath() {
		return privateKeyPath;
	}
	public void setPrivateKeyPath(String privateKeyPath) {
		this.privateKeyPath = privateKeyPath;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public static void main(String[] args) {
		
	}
}
