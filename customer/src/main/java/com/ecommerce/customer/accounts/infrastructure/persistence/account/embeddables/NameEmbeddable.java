package com.ecommerce.customer.accounts.infrastructure.persistence.account.embeddables;

import jakarta.persistence.Embeddable;

@Embeddable
public record NameEmbeddable(String value) {
	public static NameEmbeddable create(String value) {
		return new NameEmbeddable(value);
	}
}
