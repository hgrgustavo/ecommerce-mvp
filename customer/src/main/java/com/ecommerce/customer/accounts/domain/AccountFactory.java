package com.ecommerce.customer.accounts.domain;

import java.util.UUID;

public interface AccountFactory<T> {
	T create(UUID uuid, String name, String[] credentials);
}
