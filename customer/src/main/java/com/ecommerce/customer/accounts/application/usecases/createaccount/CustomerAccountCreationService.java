package com.ecommerce.customer.accounts.application.usecases.createaccount;

import static lombok.AccessLevel.PRIVATE;

import org.springframework.stereotype.Service;

import com.ecommerce.customer.accounts.domain.CustomerAccount;
import com.ecommerce.customer.accounts.domain.LoginCredentials;
import com.ecommerce.customer.accounts.domain.Name;
import com.ecommerce.customer.accounts.domain.ports.AccountFactory;
import com.ecommerce.customer.accounts.domain.ports.AccountRepository;
import com.ecommerce.customer.accounts.domain.ports.UUIDGenerator;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public final class CustomerAccountCreationService implements CreateCustomerAccountUseCase {
	AccountRepository<CustomerAccount> repository;
	AccountFactory<CustomerAccount> factory;

	@Override
	public void execute(CustomerInputDTO input) {
		final Name name = Name.create(input.name());
		final LoginCredentials credentials = LoginCredentials.create(input.email(), input.password());
		final CustomerAccount account = factory.create(name, credentials);
		repository.save(account);
	}
}
