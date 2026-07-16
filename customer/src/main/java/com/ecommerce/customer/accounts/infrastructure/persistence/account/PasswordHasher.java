package com.ecommerce.customer.accounts.infrastructure.persistence.account;

public interface PasswordHasher { // REFACTOR: move this to a usecase
	String hash(String raw);
	boolean matches(String inputPassword, String persistedPassword);
}
