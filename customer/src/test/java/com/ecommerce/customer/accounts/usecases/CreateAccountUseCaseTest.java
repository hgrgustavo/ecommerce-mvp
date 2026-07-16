package com.ecommerce.customer.accounts.usecases;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.customer.accounts.domain.credentials.LoginCredentials;
import com.ecommerce.customer.accounts.domain.credentials.Name;
import com.ecommerce.customer.accounts.domain.types.CustomerAccount;
import com.ecommerce.customer.accounts.usecases.createaccount.CustomerAccountCreationService;
import com.ecommerce.customer.accounts.usecases.createaccount.CustomerAccountFactoryImpl;
import com.ecommerce.customer.accounts.usecases.createaccount.CustomerAccountRepositoryImpl;
import com.ecommerce.customer.accounts.usecases.createaccount.CustomerInputDTO;

import lombok.experimental.FieldDefaults;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level=PRIVATE)
public final class CreateAccountUseCaseTest {
	@Mock
	CustomerAccountRepositoryImpl repository;

	@Mock
	CustomerAccountFactoryImpl factory;

	@InjectMocks
	CustomerAccountCreationService service;

	@Captor 
	ArgumentCaptor<CustomerAccount> captor;

	@ParameterizedTest
	@MethodSource("customerInputDTO")
	void customerAccountWasCreatedWithHashedPassword(CustomerInputDTO input) {
		String expectedHash = "$argon2id$v=19$m=16384,t=2,p=1$fakeHash";

		when(factory.create(any(), any(), any()))
		.thenReturn(new CustomerAccount(
				input.uuid(), 
				Name.create(input.name()), 
				LoginCredentials.create(input.email(), expectedHash)));

		service.execute(input);

		verify(repository).save(captor.capture());

		CustomerAccount savedAccount = captor.getValue();

		assertThat(savedAccount.getCredentials().password())
		.isNotEqualTo(input.password())
		.startsWith("$argon2id$");
	}

	private static Stream<Arguments> customerInputDTO() {
		return Stream.of(
				Arguments.of(
						CustomerInputDTO.create(
								"Gustavo Henrique", 
								"gustavosvalidemail@gmail.com", 
								"$StrongPasswordThatllBeHashed123$")
						)
				);	
	}
}