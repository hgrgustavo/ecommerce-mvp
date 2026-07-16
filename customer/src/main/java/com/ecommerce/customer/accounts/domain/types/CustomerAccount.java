package com.ecommerce.customer.accounts.domain.types;

import static com.ecommerce.customer.accounts.domain.credentials.RoleType.CUSTOMER;

import java.util.UUID;

import com.ecommerce.customer.accounts.domain.credentials.LoginCredentials;
import com.ecommerce.customer.accounts.domain.credentials.Name;
import com.ecommerce.customer.accounts.domain.credentials.Role;

public final class CustomerAccount extends Account {	
	public CustomerAccount(UUID uuid, Name name, LoginCredentials credentials) {
		super(uuid, name, Role.define(CUSTOMER), credentials);
	}
}