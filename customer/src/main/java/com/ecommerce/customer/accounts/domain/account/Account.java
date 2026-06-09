package com.ecommerce.customer.accounts.domain.account;

import java.util.UUID;

abstract sealed class Account permits CustomerAccount {
	private final UUID uuid;
	private Roles role;
	private LoginCredentials credentials;
	
	Account(UUID uuid, Roles role, LoginCredentials credentials) {
		if (uuid == null) throw new IllegalArgumentException("Account identity is required.");
        if (role == null) throw new IllegalArgumentException("The account needs an access rule (Role).");
        if (credentials == null) throw new IllegalArgumentException("Login credentials cannot be invalid.");
		
        this.uuid = uuid;
		this.role = role;
		this.credentials = credentials;
	}

	Roles getRole() {
		return role;
	}
}
