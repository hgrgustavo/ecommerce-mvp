package com.ecommerce.customer.accounts.domain;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository<T> {
	Optional<T> findById(UUID uuid);
	Optional<T> findByEmail(String email);
	void save(T account);
}