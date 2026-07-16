package com.ecommerce.customer.accounts.usecases.login;

import java.util.UUID;

public record CustomerLoginOutputDTO(UUID uuid, String name, String email) {
	public static CustomerLoginOutputDTO create(UUID uuid, String name, String email) {
		return new CustomerLoginOutputDTO(uuid, name, email);
	}
}
