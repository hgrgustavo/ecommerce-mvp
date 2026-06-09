package com.ecommerce.customer.accounts.domain.account;

import java.util.regex.Pattern;

record LoginCredentials(String name, String email, String password) {
	private static final Pattern NAME_PATTERN = Pattern.compile(
	        "^[A-Za-zÀ-ÖØ-öø-ÿ]+([ '][A-Za-zÀ-ÖØ-öø-ÿ]+)+$"
	);
	
	private static final Pattern EMAIL_PATTERN = Pattern.compile(
	        "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"
	);
	
	private static final Pattern PASSWORD_PATTERN = Pattern.compile(
	        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$"
	);
	
	LoginCredentials {
		String[] credentials = { name, email, password };
		
		sanitize(credentials);
		
		if (name == null || !NAME_PATTERN.matcher(name).matches())
            throw new IllegalArgumentException("Invalid name. Please enter your full name (minimum of two words), without numbers or special characters.");
		
        if (email == null || !EMAIL_PATTERN.matcher(email).matches())
            throw new IllegalArgumentException("Invalid email format.");
        
        if (password == null || !PASSWORD_PATTERN.matcher(password).matches())
            throw new IllegalArgumentException("The password must contain at least 8 characters, including uppercase letters, lowercase letters, and numbers.");
    }

	private final void sanitize(String[] credentials) {
		for (String c : credentials) {
			c.trim();
		}
	}
}
