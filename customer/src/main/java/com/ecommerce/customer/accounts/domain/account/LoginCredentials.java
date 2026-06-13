package com.ecommerce.customer.accounts.domain.account;

import java.util.regex.Pattern;

public record LoginCredentials(String email, String password) {
	private static final Pattern EMAIL_PATTERN = Pattern.compile(
	        "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"
	);
	
	private static final Pattern PASSWORD_PATTERN = Pattern.compile(
	        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$"
	);
	
	public LoginCredentials {
		String[] credentials = { email, password };
		
		sanitize(credentials);
		
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
	
	public static LoginCredentials create(String email, String password) {
		return new LoginCredentials(email, password);
	}
}
