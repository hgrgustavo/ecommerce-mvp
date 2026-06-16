package com.ecommerce.customer.accounts.infrastructure.mappers;

import java.util.UUID;

import com.ecommerce.customer.accounts.domain.CustomerAccount;
import com.ecommerce.customer.accounts.infrastructure.dtos.CustomerCartDTO;
import com.ecommerce.customer.accounts.infrastructure.dtos.CustomerInputDTO;

final class AccountDTOMapper {
	private AccountDTOMapper() {}

	public static CustomerCartDTO createDTOFrom(CustomerAccount account) {
		return new CustomerCartDTO(
				account.getUuid(),
				account.getName().value()
		);
	}

	public static CustomerInputDTO createDTOFrom(UUID uuid, String name, String email, String password) {
		return new CustomerInputDTO(uuid, name, email, password);
	}
}