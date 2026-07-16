package com.ecommerce.customer.accounts.usecases.login;

// REFACTOR: Create a generic super-interface like LoginUseCases<T extends AccountDTO>
public interface LoginACustomerUseCase {
	CustomerLoginOutputDTO execute(CustomerLoginInputDTO dto);
}