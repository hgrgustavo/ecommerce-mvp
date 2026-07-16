package com.ecommerce.customer.accounts.usecases.createaccount;

import static lombok.AccessLevel.PRIVATE;

import java.util.UUID;

import com.ecommerce.customer.accounts.domain.CustomerAccountFactory;
import com.ecommerce.customer.accounts.domain.credentials.LoginCredentials;
import com.ecommerce.customer.accounts.domain.credentials.Name;
import com.ecommerce.customer.accounts.domain.types.CustomerAccount;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=PRIVATE, makeFinal=true)
@RequiredArgsConstructor
public final class CustomerAccountFactoryImpl implements CustomerAccountFactory {
	@Override
	public CustomerAccount create(UUID uuid, String name, String[] credentials) {
		return new CustomerAccount(
				uuid, 
				Name.create(name), 
				LoginCredentials.create(credentials[0], credentials[1]));
	}
}