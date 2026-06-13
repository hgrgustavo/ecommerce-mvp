package com.ecommerce.customer.accounts.infrastructure.persistence.valueobjects;

public record Role(RoleType type) {
	public static Role define(RoleType type) {
		return new Role(type);
	}
}