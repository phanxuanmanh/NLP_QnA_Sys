package model;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;


public class ReciveFile {
	public static String SERVER_PACK = "E:\\Test\\server";
	public static int PORT = 1234;
	DataInputStream dis;
	DataOutputStream dos;
	BufferedOutputStream bos;
	private  ServerSocket ss;
	Socket socket;

	public void OpenServer(){
		try {
			ss = new ServerSocket(1234);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public  Socket getConnection() throws IOException {
		socket=ss.accept();
		dis= new DataInputStream(socket.getInputStream());
		dos= new DataOutputStream(socket.getOutputStream());
	   return socket;
	}
	public  X509Certificate getCert(){
		try {
			getConnection();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(socket!=null){
			try {
				int fileSize = (int) dis.readLong();
				System.out.println(fileSize);
				byte[] keyBytes=new byte[fileSize];
				dis.read(keyBytes);
				X509Certificate cert = X509Certificate.getInstance(keyBytes);
				return cert;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (CertificateException e) {
				e.printStackTrace();
			}
		}
		
		return null;	
	}
	public String getMd5(){
		try {
			dos.writeInt(2);
			dos.flush();
			return dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getPassword(){
		try {
			dos.writeInt(3);
			return dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getFile(String folder){
		BufferedOutputStream bos;
		long byteReaded = 0;
		long byteMustRead;
		byte[] buffer;
		int i;
		String fileName = null;
		try {
			dos.writeInt(4);
			fileName =dis.readUTF();
			System.out.println(fileName);
			bos= new BufferedOutputStream(new FileOutputStream("inbox/"+fileName));
			long size= dis.readLong();
			byteMustRead = (size - byteReaded) > 1024 ? 1024
					: (size - byteReaded);
			System.out.println(byteMustRead);
			buffer = new byte[(int) byteMustRead];
			System.out.println(fileName);
			while (byteMustRead != 0) {
				i = dis.read(buffer);
				bos.write(buffer, 0, i);
				byteReaded += i;
				byteMustRead = (size - byteReaded) > 1024 ? 1024
						: (size - byteReaded);
			}
			bos.flush();
			bos.close();
			System.out.println("compleate");
		} catch (Exception e) {
		}
		return "inbox/"+ fileName;
	}
	public void deny(){
		try {
			socket.close();
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		ReciveFile r = new ReciveFile();
		r.OpenServer();
		System.out.println(r.getCert());
	}
	public String getSocketAddress(){
		return socket.getInetAddress().toString();
	}
}
