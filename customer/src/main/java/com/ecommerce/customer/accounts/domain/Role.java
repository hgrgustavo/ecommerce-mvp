package com.ecommerce.customer.accounts.domain;

public record Role(RoleType type) {
	public static Role define(RoleType type) {
		return new Role(type);
	}
}