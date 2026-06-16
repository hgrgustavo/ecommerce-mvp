package com.ecommerce.customer.accounts.domain.ports;

import com.ecommerce.customer.accounts.domain.Account;

@FunctionalInterface
public interface AccountFactory<T extends Account> {
	T create();
}
