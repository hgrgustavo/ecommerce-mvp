package com.ecommerce.customer.accounts.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecommerce.customer.accounts.domain.CustomerAccountFactory;
import com.ecommerce.customer.accounts.domain.CustomerAccountRepository;
import com.ecommerce.customer.accounts.infrastructure.persistence.account.CustomerAccountDataMapper;
import com.ecommerce.customer.accounts.infrastructure.persistence.account.CustomerAccountDataMapperImpl;
import com.ecommerce.customer.accounts.infrastructure.persistence.account.PasswordHasher;
import com.ecommerce.customer.accounts.infrastructure.persistence.account.SpringDataAccountRepository;
import com.ecommerce.customer.accounts.usecases.createaccount.CreateCustomerAccountUseCase;
import com.ecommerce.customer.accounts.usecases.createaccount.CustomerAccountCreationService;
import com.ecommerce.customer.accounts.usecases.createaccount.CustomerAccountFactoryImpl;
import com.ecommerce.customer.accounts.usecases.createaccount.CustomerAccountRepositoryImpl;
import com.ecommerce.customer.accounts.usecases.login.CustomerAccountLoginService;
import com.ecommerce.customer.accounts.usecases.login.LoginACustomerUseCase;

@Configuration
class AccountConfiguration {
    @Bean
    CustomerAccountFactory customerAccountFactory() {
        return new CustomerAccountFactoryImpl();
    }
    
    @Bean
    CustomerAccountDataMapper accountDataMapper(CustomerAccountFactory factory) {
    	return new CustomerAccountDataMapperImpl(factory);
    }
    
    @Bean
    CustomerAccountRepository customerAccountRepository(
            SpringDataAccountRepository repository,
            CustomerAccountDataMapper mapper) {
        return new CustomerAccountRepositoryImpl(repository, mapper);
    }
    
    @Bean
    CreateCustomerAccountUseCase createCustomerAccountUseCase(
            CustomerAccountRepository repository,
            CustomerAccountFactory factory,
            PasswordHasher hasher) {
        return new CustomerAccountCreationService(repository, factory, hasher);
    }
    
    @Bean
    LoginACustomerUseCase loginACustomerUseCase(CustomerAccountRepository repository, PasswordHasher hasher) {
    	return new CustomerAccountLoginService(repository, hasher);
    }
}