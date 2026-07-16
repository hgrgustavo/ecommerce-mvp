package com.ecommerce.customer.accounts.infrastructure.persistence.outbox;

public enum OutboxStatus {
	PENDING,
    PROCESSED,
    FAILED
}
