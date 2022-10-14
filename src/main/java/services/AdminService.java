package services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.ejb.Stateless;

@Stateless
public class AdminService {
	
	/**
	 * @param clearText
	 * @return Base 64 encoded hashed with SHA-256 algo clearText
	 * @throws NoSuchAlgorithmException
	 */
	public static String encode(final String clearText) throws NoSuchAlgorithmException {
	    return new String(
	            Base64.getEncoder().encode(MessageDigest.getInstance("SHA-256").digest(clearText.getBytes(StandardCharsets.UTF_8))));
	}

}
