package mahoablock;

public class Mode {

	public static byte ceasarEn(byte input, short key) {
		short a = input;
		a = (short) ((a + key) % 256);
		return (byte) a;
	}

	public static byte ceasarDe(byte input, short key) {
		if((input-key)>0){
			return (byte)(input-key);
		}else{
			return (byte) (255-(input-key));
		}
	}

	public static byte[] EBC_Encrypt(byte[] plan, short key) {
		byte[] out = new byte[plan.length];
		for (int i = 0; i < plan.length; i++) {
			out[i] = ceasarEn(plan[i], key);
		}
		return out;
	}

	public static byte[] EBC_Decrypt(byte[] cipher, short key) {
		byte[] out = new byte[cipher.length];
		for (int i = 0; i < cipher.length; i++) {
			out[i] = ceasarDe(cipher[i], key);
		}
		return out;
	}

	public static byte[] encrypt_CBC(byte iv, short key, byte[] plan) {
		byte[] out = new byte[plan.length];
		out[0] = ceasarEn((byte) (plan[0] ^ iv), key);
		for (int i = 1; i < plan.length; i++) {
			out[i] = ceasarEn((byte) (plan[i] ^ out[i - 1]), key);
		}
		return out;
	}

	public static byte[] decrypt_CBC(byte iv, short key, byte[] cipher) {
		byte[] out = new byte[cipher.length];
		for(int i = 0; i<cipher.length;i++){
			out[i] = ceasarDe(cipher[i], key);
		}
		for (int i = cipher.length - 1; i > 0; i--) {
			out[i] = (byte) (out[i] ^ cipher[i - 1]);
		}
		out[0] = (byte) (out[0] ^ iv);
		return out;
	}
	public static void main(String[] args) {
		byte[] test =EBC_Decrypt(EBC_Encrypt("toi di hoc".getBytes(), (short) 5), (short) 5);
		String out = new String(test);
		System.out.println(out);
		byte[] test2 = decrypt_CBC((byte)0, (short)5,encrypt_CBC((byte)0,(short) 5, "Toi di choi".getBytes()));
		String out2 = new String(test2);
		System.out.println(out2);
	}
	
}
