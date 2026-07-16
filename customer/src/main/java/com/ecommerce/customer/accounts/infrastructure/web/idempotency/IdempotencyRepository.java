package com.ecommerce.customer.accounts.infrastructure.web.idempotency;

import java.util.Optional;

public interface IdempotencyRepository {
	boolean tryLock(IdempotencyKey key);
	void unlock(IdempotencyKey key);
	void saveResponse(IdempotencyKey key, String responseBody);
	Optional<String> getResponse(IdempotencyKey key);
}