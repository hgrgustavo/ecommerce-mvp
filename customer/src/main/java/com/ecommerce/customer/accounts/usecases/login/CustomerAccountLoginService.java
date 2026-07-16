package com.ecommerce.customer.accounts.usecases.login;

import static lombok.AccessLevel.PRIVATE;

import java.util.UUID;

import org.springframework.security.authentication.BadCredentialsException;

import com.ecommerce.customer.accounts.domain.CustomerAccountRepository;
import com.ecommerce.customer.accounts.infrastructure.persistence.account.PasswordHasher;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=PRIVATE, makeFinal=true)
@RequiredArgsConstructor
public class CustomerAccountLoginService implements LoginACustomerUseCase {
	CustomerAccountRepository repository;
	PasswordHasher encoder;

	@Override
	public CustomerLoginOutputDTO execute(CustomerLoginInputDTO input) {
		final String inputPassword = input.credentials().password();
		
		return repository
				.findByEmail(input.credentials().email())
				.filter(account -> {
					final String persistedPassword = account.getCredentials().password();
					return encoder.matches(inputPassword, persistedPassword);
				})
				.map(account -> {
					final String name = account.getName().value();
					final String email = account.getCredentials().email();
					final UUID uuid = account.getUuid();
					return CustomerLoginOutputDTO.create(uuid, name, email);
				})
				.orElseThrow(() -> new BadCredentialsException("Invalid username or password."));
	}
}