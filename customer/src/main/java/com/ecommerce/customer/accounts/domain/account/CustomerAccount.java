package com.ecommerce.customer.accounts.domain.account;

import static com.ecommerce.customer.accounts.domain.account.RoleType.CUSTOMER;

import java.util.UUID;

final class CustomerAccount extends Account {	
	CustomerAccount(UUID uuid, LoginCredentials credentials) {
		super(uuid, Roles.define(CUSTOMER), credentials);
	}
}
