package com.ecommerce.customer.accounts.infrastructure.persistence.jpa.converters;

import java.util.function.Function;

import com.ecommerce.customer.accounts.domain.LoginCredentials;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public final class LoginCredentialsToStringArrayConverter implements AttributeConverter<LoginCredentials, String[]> {
	@Override
	public String[] convertToDatabaseColumn(LoginCredentials credentials) {
		Function<LoginCredentials, String[]> function = lc -> {
			return (lc.email() == null || lc.email().isBlank()) && 
					(lc.password() == null || lc.password().isBlank())
		            ? null
		            : new String[] { lc.email(), lc.password() };
		};
		
		return function.apply(credentials);
	}

	@Override
	public LoginCredentials convertToEntityAttribute(String[] dbData) {
		Function<String[], LoginCredentials> function = s -> {
			for(String c : s) {
				if(c == null || c.isBlank())
					c = null;
			}
			
			return LoginCredentials.create(s[0], s[1]);
		};
		
		return function.apply(dbData);
	}
}
