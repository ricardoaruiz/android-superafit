package superafit.rar.com.br.superafit.uitls;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CriptoUtil {
	
	public static String cripto(String senha) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(senha.getBytes());
			byte[] hash = md.digest();
			StringBuilder hexString = new StringBuilder();
			for (int i = 0; i < hash.length; i++) {
			     if ((0xff & hash[i]) < 0x10) {
			         hexString.append( "0" + Integer.toHexString((0xFF & hash[i])));
			     } else {
			    	 hexString.append(Integer.toHexString(0xFF & hash[i]));
			     }
			}			
			return hexString.toString();			
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
		
}
