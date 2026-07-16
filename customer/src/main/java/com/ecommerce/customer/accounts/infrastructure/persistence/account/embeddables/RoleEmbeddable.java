package com.ecommerce.customer.accounts.infrastructure.persistence.account.embeddables;

import com.ecommerce.customer.accounts.domain.credentials.RoleType;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;

@Embeddable
public record RoleEmbeddable(@Enumerated RoleType type) {
	public static RoleEmbeddable define(RoleType type) {
		return new RoleEmbeddable(type);
	}
}