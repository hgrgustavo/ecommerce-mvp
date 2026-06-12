package com.ecommerce.customer.accounts.domain.account;

record Roles(RoleType type) {
	static Roles define(RoleType type) {
		return new Roles(type);
	}
}