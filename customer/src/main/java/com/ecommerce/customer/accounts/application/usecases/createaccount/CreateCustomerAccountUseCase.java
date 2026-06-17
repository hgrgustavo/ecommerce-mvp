package com.ecommerce.customer.accounts.application.usecases.createaccount;

import com.ecommerce.customer.accounts.infrastructure.dtos.CustomerInputDTO;

public sealed interface CreateCustomerAccountUseCase permits CustomerAccountCreationService {
	void execute(CustomerInputDTO input);
}