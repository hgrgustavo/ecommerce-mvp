package com.ecommerce.customer.accounts.usecases;

import static lombok.AccessLevel.PRIVATE;
import static org.mockito.ArgumentMatchers.any;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.customer.accounts.application.factories.CustomerAccountFactory;
import com.ecommerce.customer.accounts.application.usecases.createaccount.CustomerAccountCreationService;
import com.ecommerce.customer.accounts.application.usecases.createaccount.CustomerInputDTO;
import com.ecommerce.customer.accounts.domain.CustomerAccount;
import com.ecommerce.customer.accounts.domain.LoginCredentials;
import com.ecommerce.customer.accounts.domain.Name;
import com.ecommerce.customer.accounts.domain.ports.AccountFactory;
import com.ecommerce.customer.accounts.domain.ports.AccountRepository;

import lombok.experimental.FieldDefaults;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = PRIVATE)
public final class CreateAccountUseCaseTest {
	@Mock
	AccountRepository<CustomerAccount> repository;

	@Mock
	AccountFactory<CustomerAccount> factory;

	@InjectMocks
	CustomerAccountCreationService service;

	@ParameterizedTest
	@MethodSource("customerInputDTOFactory")
	void customerAccountWasCreated(CustomerInputDTO input) {
		service.execute(input);
		Mockito.verify(factory).create(any(Name.class), any(LoginCredentials.class));
		Mockito.verify(repository).save(any(CustomerAccount.class));
	}

	private static Stream<Arguments> customerInputDTOFactory() {
		return Stream.of(
				Arguments.of(
						CustomerInputDTO.create(
								"Gustavo Henrique", 
								"gustavosvalidemail@gmail.com", 
								"$strongpasswordthatllbehashed$")
						)
				);	
	}
}