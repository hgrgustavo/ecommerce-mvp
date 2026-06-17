package com.ecommerce.customer.accounts.application.usecases.createaccount;

public sealed interface CreateCustomerAccountUseCase permits CustomerAccountCreationService {
	void execute(CustomerInputDTO input);
}