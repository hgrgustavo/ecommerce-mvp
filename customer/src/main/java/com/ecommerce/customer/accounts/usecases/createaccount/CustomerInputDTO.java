package com.ecommerce.customer.accounts.usecases.createaccount;

import java.util.UUID;

public record CustomerInputDTO(UUID uuid, String name, String email, String password) {
	public static CustomerInputDTO create(String name, String email, String password) {
		final UUID randomUUID = UUID.randomUUID();
		return new CustomerInputDTO(randomUUID, name, email, password);
	}
}