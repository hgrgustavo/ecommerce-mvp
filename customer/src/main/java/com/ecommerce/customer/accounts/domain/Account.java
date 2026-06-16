package com.ecommerce.customer.accounts.domain;

import java.util.UUID;

public abstract sealed class Account permits CustomerAccount {
	private final UUID uuid;
	private Roles role;
	private Name name;
	private LoginCredentials credentials;
	
	Account(UUID uuid, Name name, Roles role, LoginCredentials credentials) {
		if (uuid == null) throw new IllegalArgumentException("Account identity is required.");
		if (name == null) throw new IllegalArgumentException("Account name is required.");
        if (role == null) throw new IllegalArgumentException("The account needs an access rule (Role).");
        if (credentials == null) throw new IllegalArgumentException("Login credentials cannot be invalid.");
		
        this.uuid = uuid;
		this.role = role;
		this.name = name;
		this.credentials = credentials;
	}
	
	public Name getName() {
		return name;
	}

	Roles getRole() {
		return role;
	}

	public UUID getUuid() {
		return uuid;
	}

	public LoginCredentials getCredentials() {
		return credentials;
	}
	
	
}
