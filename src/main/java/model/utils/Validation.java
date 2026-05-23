package model.utils;

import java.util.regex.Pattern;

public class Validation {
	private static String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	private static String TELEFONO_REGEX = "^\\+?[1-9]\\d{6,14}$";
	
	public static boolean validateEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
	    if (email == null) {
	        return false;
	    }
	    return pattern.matcher(email).matches();
	}
	
	public static boolean validateTelefono(String telefono) {
		Pattern pattern = Pattern.compile(TELEFONO_REGEX);
	    if (telefono == null) {
	        return false;
	    }
	    
	    String telefonoPulito = telefono.replaceAll("[\\s\\-\\(\\)]", "");

        if (telefonoPulito.startsWith("00")) {
            telefonoPulito = "+" + telefonoPulito.substring(2);
        }
        
	    return pattern.matcher(telefono).matches();
	}
}
