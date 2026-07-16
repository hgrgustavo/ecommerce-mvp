package com.ecommerce.customer.accounts.usecases.login;

import com.ecommerce.customer.accounts.domain.credentials.LoginCredentials;

public record CustomerLoginInputDTO(LoginCredentials credentials) {
	public static CustomerLoginInputDTO create(LoginCredentials credentials) {
		return new CustomerLoginInputDTO(credentials);
	}
}