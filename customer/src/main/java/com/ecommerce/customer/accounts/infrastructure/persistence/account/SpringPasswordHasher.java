package com.ecommerce.customer.accounts.infrastructure.persistence.account;

import static lombok.AccessLevel.PRIVATE;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=PRIVATE, makeFinal=true)
@RequiredArgsConstructor
public class SpringPasswordHasher implements PasswordHasher {
	PasswordEncoder encoder;
	
	@Value("${crypto.hash.pepper")
	static String HASH_PEPPER;
	
	@Override
	public String hash(String raw) {
		return encoder.encode(raw) + HASH_PEPPER;
	}

	@Override
	public boolean matches(String inputPassword, String persistedPassword) {
		return encoder.matches(inputPassword, persistedPassword);
	}
}
