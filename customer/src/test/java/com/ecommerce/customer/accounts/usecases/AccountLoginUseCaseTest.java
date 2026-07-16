package com.ecommerce.customer.accounts.usecases;

import static com.ecommerce.customer.accounts.usecases.login.CustomerLoginInputDTO.create;
import static lombok.AccessLevel.PRIVATE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.customer.accounts.domain.CustomerAccountRepository;
import com.ecommerce.customer.accounts.domain.credentials.LoginCredentials;
import com.ecommerce.customer.accounts.domain.credentials.Name;
import com.ecommerce.customer.accounts.domain.types.CustomerAccount;
import com.ecommerce.customer.accounts.infrastructure.persistence.account.PasswordHasher;
import com.ecommerce.customer.accounts.usecases.login.CustomerAccountLoginService;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE)
@ExtendWith(MockitoExtension.class)
class AccountLoginUseCaseTest {
    @Mock
    CustomerAccountRepository repository;

    @Mock
    PasswordHasher hasher;

    @InjectMocks
    CustomerAccountLoginService service;

    @Test
    @DisplayName("It should log in when the credentials are valid.")
    void shouldLoginSuccessfully_whenCredentialsAreValid() {
        final String email = "gustavo@email.com";
        final String password = "$Val1dP4ssw0rd$";

        CustomerAccount account = new CustomerAccount(
                null,
                Name.create("Gustavo Henrique"),
                LoginCredentials.create(email, password)
        );

        when(repository.findByEmail(email)).thenReturn(Optional.of(account));  
        when(hasher.matches(password, account.getCredentials().password())).thenReturn(true);

        assertDoesNotThrow(() -> service.execute(create(account.getCredentials())));

        verify(repository).findByEmail(email);
        verify(hasher).matches(password, account.getCredentials().password());
    }
}