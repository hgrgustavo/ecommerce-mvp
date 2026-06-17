package com.ecommerce.customer.accounts.infrastructure.persistence.jpa.valueobjects;

public enum OutboxStatus {
	PENDING,
    PROCESSED,
    FAILED
}
