package com.ecommerce.customer.accounts.domain.account;

import static com.ecommerce.customer.accounts.domain.account.RoleType.CUSTOMER;

import java.util.UUID;

public final class CustomerAccount extends Account {	
	public CustomerAccount(UUID uuid, Name name, LoginCredentials credentials) {
		super(uuid, name, Roles.define(CUSTOMER), credentials);
	}
}
