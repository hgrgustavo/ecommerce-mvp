package com.ecommerce.customer.accounts.domain.types;

import java.util.UUID;

import com.ecommerce.customer.accounts.domain.credentials.LoginCredentials;
import com.ecommerce.customer.accounts.domain.credentials.Name;
import com.ecommerce.customer.accounts.domain.credentials.Role;

public abstract sealed class Account permits CustomerAccount {
	private final UUID uuid;
	private Role role;
	private Name name;
	private LoginCredentials credentials;
	
	Account(UUID uuid, Name name, Role role, LoginCredentials credentials) {
		if (name == null) throw new IllegalArgumentException("Account name is required.");
        if (role == null) throw new IllegalArgumentException("The account needs an access rule (RoleEmbeddable).");
        if (credentials == null) throw new IllegalArgumentException("Login credentials cannot be invalid.");
		
        this.uuid = uuid;
		this.role = role;
		this.name = name;
		this.credentials = credentials;
	}
	
	public Name getName() {
		return name;
	}

	public Role getRole() {
		return role;
	}

	public UUID getUuid() {
		return uuid;
	}

	public LoginCredentials getCredentials() {
		return credentials;
	}
	
	
}
