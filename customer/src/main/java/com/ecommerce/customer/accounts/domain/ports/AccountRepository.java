package com.ecommerce.customer.accounts.domain.ports;

import java.util.Optional;
import java.util.UUID;

import com.ecommerce.customer.accounts.domain.Account;

public interface AccountRepository {
	Optional<Account> findById(UUID uuid);
	void save(Account account);
}
