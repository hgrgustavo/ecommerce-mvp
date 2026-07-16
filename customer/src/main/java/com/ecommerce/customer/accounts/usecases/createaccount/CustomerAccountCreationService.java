package com.ecommerce.customer.accounts.usecases.createaccount;

import static lombok.AccessLevel.PRIVATE;

import com.ecommerce.customer.accounts.domain.CustomerAccountFactory;
import com.ecommerce.customer.accounts.domain.CustomerAccountRepository;
import com.ecommerce.customer.accounts.domain.types.CustomerAccount;
import com.ecommerce.customer.accounts.infrastructure.persistence.account.PasswordHasher;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=PRIVATE, makeFinal=true)
@RequiredArgsConstructor
public class CustomerAccountCreationService implements CreateCustomerAccountUseCase {
	CustomerAccountRepository repository;
	CustomerAccountFactory factory;
	PasswordHasher hasher;

	@Override
	public CustomerOutputDTO execute(CustomerInputDTO input) {
		String hash = hasher.hash(input.password());
		final CustomerAccount account = factory.create(
				null,
				input.name(),
				new String[] {
						input.email(), 
						hash
				});		
		repository.save(account);
		return CustomerOutputDTO.create(
				account.getName().value(),
				account.getCredentials().email());
	}
}