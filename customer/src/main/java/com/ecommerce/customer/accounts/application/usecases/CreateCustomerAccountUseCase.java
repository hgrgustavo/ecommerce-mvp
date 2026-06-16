package com.ecommerce.customer.accounts.application.usecases;

import com.ecommerce.customer.accounts.infrastructure.dtos.CustomerInputDTO;

public interface CreateCustomerAccountUseCase {
	void execute(CustomerInputDTO input);
}