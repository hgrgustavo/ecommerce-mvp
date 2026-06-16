package com.ecommerce.customer.accounts.domain;

record Roles(RoleType type) {
	static Roles define(RoleType type) {
		return new Roles(type);
	}
}