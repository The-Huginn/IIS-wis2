package services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ejb.Stateless;

@Stateless
public class AdminService {
	
	public String encode(final String clearText) throws NoSuchAlgorithmException {

		byte messageDigest[] = MessageDigest.getInstance("MD5").digest(clearText.getBytes(StandardCharsets.UTF_8));

		StringBuffer hexString = new StringBuffer();
        for (int i=0; i<messageDigest.length; i++)
            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
        return hexString.toString();
	}

}
