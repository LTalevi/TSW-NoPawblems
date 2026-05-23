package model.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encryption {
	public static String hashPassword(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes());
			
			return Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException n){
			System.err.println("Algoritmo SHA-256 non trovato:" + n.getMessage());
			throw new RuntimeException("Errore durante hashing password", n);
		}
	}
}
