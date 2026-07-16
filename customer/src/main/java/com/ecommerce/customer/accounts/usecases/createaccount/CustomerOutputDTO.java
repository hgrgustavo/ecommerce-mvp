package com.ecommerce.customer.accounts.usecases.createaccount;

public record CustomerOutputDTO(String name, String email) {
	public static CustomerOutputDTO create(String name, String email) {
		return new CustomerOutputDTO(name, email);
	}
}