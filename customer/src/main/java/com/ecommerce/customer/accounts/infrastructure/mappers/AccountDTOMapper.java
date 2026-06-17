package com.ecommerce.customer.accounts.infrastructure.mappers;

import static lombok.AccessLevel.PRIVATE;

import com.ecommerce.customer.accounts.application.usecases.createaccount.CustomerInputDTO;
import com.ecommerce.customer.accounts.domain.CustomerAccount;
import com.ecommerce.customer.accounts.infrastructure.dtos.CustomerCartDTO;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
final class AccountDTOMapper {

	public static CustomerCartDTO createDTOFrom(CustomerAccount account) {
		return new CustomerCartDTO(
				account.getUuid(),
				account.getName().value()
		);
	}

	public static CustomerInputDTO createDTOFrom(String name, String email, String password) {
		return new CustomerInputDTO(name, email, password);
	}
}