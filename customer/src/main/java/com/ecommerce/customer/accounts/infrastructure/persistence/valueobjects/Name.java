package com.ecommerce.customer.accounts.infrastructure.persistence.valueobjects;

import java.util.regex.Pattern;

public record Name(String value) {
	private static final Pattern NAME_PATTERN = Pattern.compile(
	        "^[A-Za-zÀ-ÖØ-öø-ÿ]+([ '][A-Za-zÀ-ÖØ-öø-ÿ]+)+$"
	);
	
	public Name {
		value.trim();
		
		if (value == null || !NAME_PATTERN.matcher(value).matches())
            throw new IllegalArgumentException("Invalid name. Please enter your full name (minimum of two words), without numbers or special characters.");
	}
	
	public static Name create(String value) {
		return new Name(value);
	}
}
