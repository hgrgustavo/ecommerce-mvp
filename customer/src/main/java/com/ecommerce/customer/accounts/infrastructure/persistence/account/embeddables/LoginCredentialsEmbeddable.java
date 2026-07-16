package com.ecommerce.customer.accounts.infrastructure.persistence.account.embeddables;

import jakarta.persistence.Embeddable;

@Embeddable
public record LoginCredentialsEmbeddable(String email, String password) {
	public static LoginCredentialsEmbeddable create(String email, String password) {
		return new LoginCredentialsEmbeddable(email, password);
	}
}
