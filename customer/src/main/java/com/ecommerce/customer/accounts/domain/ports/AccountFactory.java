package com.ecommerce.customer.accounts.domain.ports;

import java.util.UUID;

import com.ecommerce.customer.accounts.domain.Account;
import com.ecommerce.customer.accounts.domain.LoginCredentials;
import com.ecommerce.customer.accounts.domain.Name;

public interface AccountFactory<T extends Account> {
	T create(Name name, LoginCredentials credentials);
	T recreate(UUID uuid, Name name, LoginCredentials credentials);
}
