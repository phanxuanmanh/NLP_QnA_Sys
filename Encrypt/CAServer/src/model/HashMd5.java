package model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashMd5 {
public static String getMD5(String s) throws UnsupportedEncodingException, NoSuchAlgorithmException{
	MessageDigest md = MessageDigest.getInstance("MD5");
	byte[] infoBytes = s.getBytes("UTF-8");
	md.update(infoBytes);
	byte[] mdbytes = md.digest();
	return Base64.getEncoder().encodeToString(mdbytes);
}
}
