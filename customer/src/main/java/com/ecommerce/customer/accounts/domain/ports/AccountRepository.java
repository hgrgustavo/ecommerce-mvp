package com.ecommerce.customer.accounts.domain.ports;

import java.util.Optional;
import java.util.UUID;

import com.ecommerce.customer.accounts.domain.Account;

public interface AccountRepository<T extends Account> {
	Optional<T> findById(UUID uuid);
	void save(T account);
}
