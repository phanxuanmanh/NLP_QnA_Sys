package mahoa;

public class Vigenre {
	public static String encrypt(String plainText, String key) {
		char[] ckey = key.toCharArray();
		char[] ctext = plainText.toCharArray();
		int[] ckey2 = new int[ctext.length];
		for (int i = 0; i < ctext.length; i++) {
			int tmp = i % ckey.length;
			ckey2[i] = (int) (ckey[tmp] - 65);

		}

		for (int i = 0; i < ctext.length; i++) {
			if ((ctext[i] + ckey2[i]) <= 90) {
				ctext[i] = (char) (ctext[i] + ckey2[i]);
			} else {
				ctext[i] = (char) (ctext[i] + ckey2[i] - 26);
			}
		}
		String out = new String(ctext);
		return out;

	}

	public static String decrypt(String apherText, String key) {
		char[] ckey = key.toCharArray();
		char[] ctext = apherText.toCharArray();
		int[] ckey2 = new int[ctext.length];
		for (int i = 0; i < ctext.length; i++) {
			int tmp = i % ckey.length;
			ckey2[i] = (int) (ckey[tmp] - 65);
		}
		for (int i = 0; i < ctext.length; i++) {
			if ((ctext[i] - ckey2[i]) >= 60) {
				ctext[i] = (char) (ctext[i] - ckey2[i]);
			} else {
				ctext[i] = (char) ((ctext[i] - ckey2[i] + 26));
			}
		}

		String out = new String(ctext);
		return out;
	}

	public char getAt(int row, int colunm) {
		return '0';
	}

	public static void main(String[] args) {
		System.out.println(encrypt("ABC", "ABC"));
		System.out.println(decrypt(encrypt("HELLOCOHAI", "ILOVE"), "ILOVE"));

	}
}
