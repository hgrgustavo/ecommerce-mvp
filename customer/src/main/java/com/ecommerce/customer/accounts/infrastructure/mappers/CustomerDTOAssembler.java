package com.ecommerce.customer.accounts.infrastructure.mappers;

import java.util.UUID;

import com.ecommerce.customer.accounts.domain.account.CustomerAccount;
import com.ecommerce.customer.accounts.infrastructure.dtos.CustomerCartDTO;
import com.ecommerce.customer.accounts.infrastructure.dtos.CustomerInputDTO;

final class CustomerDTOAssembler {
	private CustomerDTOAssembler() {}

	public static CustomerCartDTO createDTOFrom(CustomerAccount account) {
		return new CustomerCartDTO(
				account.getUuid(),
				account.getName().value()
		);
	}

	/* CustomerAccount [static factory] 
	public static CustomerAccount createDomainObjectFrom(CustomerInputDTO dto) {
		return new CustomerAccount();
	}
	*/
	
	public static CustomerInputDTO createDTOFrom(UUID uuid, String name, String email, String password) {
		return new CustomerInputDTO(uuid, name, email, password);
	}
}
