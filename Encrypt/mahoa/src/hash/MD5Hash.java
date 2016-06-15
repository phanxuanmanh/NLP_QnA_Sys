package hash;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.Base64;
public class MD5Hash {
	
		

	public static String getMD5(String file){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			FileInputStream fis = new FileInputStream(new File(file));
			byte[] buff = new byte[1024];
			int c =0;
			while((c=fis.read(buff))>0){
				md.update(buff, 0, c);
			}
			fis.close();
		    byte[] digest= md.digest();
		    
		 return Base64.getEncoder().encodeToString(digest);
		   
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	
public static boolean isEdited(String file, String md5Text){
	try {
		
	    String md5 = getMD5(file);
	    return md5Text.equals(md5);
		
	} catch (Exception e) {
		e.printStackTrace();
        return false;
	}
}
public static void main(String[] args) {
//	try {
//		String md5Text = getMD5("test/file.ppt");
//		PrintWriter pr = new PrintWriter(new File("test/md5.dat"));
//		pr.write(md5Text);
//		pr.flush();
//		pr.close();
//	} catch (Exception e) {
//		// TODO: handle exception
//	}
	try {
		BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream("test/md5.dat")));
		String md5Text =read.readLine();
		read.close();
		System.out.println(isEdited("test/file.ppt", md5Text));
	} catch (Exception e) {
	}

}
}
