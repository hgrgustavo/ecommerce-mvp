package com.ecommerce.customer.accounts.application.factories;

import static lombok.AccessLevel.PRIVATE;

import java.util.UUID;

import com.ecommerce.customer.accounts.domain.CustomerAccount;
import com.ecommerce.customer.accounts.domain.LoginCredentials;
import com.ecommerce.customer.accounts.domain.Name;
import com.ecommerce.customer.accounts.domain.ports.AccountFactory;
import com.ecommerce.customer.accounts.domain.ports.UUIDGenerator;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public final class CustomerAccountFactory implements AccountFactory<CustomerAccount> {
	UUIDGenerator generator;

	@Override
	public CustomerAccount create(Name name, LoginCredentials credentials) {
		UUID uuid = generator.getV7();
		return new CustomerAccount(uuid, name, credentials);
	}

	@Override
	public CustomerAccount recreate(UUID uuid, Name name, LoginCredentials credentials) {
		return new CustomerAccount(uuid, name, credentials);
	}
}