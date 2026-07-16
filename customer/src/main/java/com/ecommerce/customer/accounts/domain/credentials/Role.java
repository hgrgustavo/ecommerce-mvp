package com.ecommerce.customer.accounts.domain.credentials;

public record Role(RoleType type) {
	public static Role define(RoleType type) {
		return new Role(type);
	}
}