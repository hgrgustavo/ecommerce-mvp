package com.ecommerce.customer.accounts.infrastructure.web.idempotency;

@SuppressWarnings("serial")
public final class IdempotencyConflictException extends Exception {
	public IdempotencyConflictException(String message) {
		super(message);
	}
}
