package vn.hcmuaf.nlp.util;

import java.io.UnsupportedEncodingException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageUtils {
	private static final String BUNDLE_NAME = "vn.hcmuaf.nlp.util.messages";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private MessageUtils() {
	}

	public static String getString(String key) {
		try {
			try {
				return new String(RESOURCE_BUNDLE.getString(key).getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
		return key;
	}
}
