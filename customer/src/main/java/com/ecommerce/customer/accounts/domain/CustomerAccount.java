package com.ecommerce.customer.accounts.domain;

import static com.ecommerce.customer.accounts.domain.RoleType.CUSTOMER;

import java.util.UUID;

public final class CustomerAccount extends Account {	
	public CustomerAccount(UUID uuid, Name name, LoginCredentials credentials) {
		super(uuid, name, Role.define(CUSTOMER), credentials);
	}
}
