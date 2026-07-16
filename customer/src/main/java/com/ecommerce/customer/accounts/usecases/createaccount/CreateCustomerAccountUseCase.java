package com.ecommerce.customer.accounts.usecases.createaccount;

public interface CreateCustomerAccountUseCase {
	CustomerOutputDTO execute(CustomerInputDTO input);
}