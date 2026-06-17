package com.ecommerce.customer.accounts.infrastructure.configuration;

import static lombok.AccessLevel.PRIVATE;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ecommerce.customer.accounts.application.factories.CustomerAccountFactory;
import com.ecommerce.customer.accounts.domain.CustomerAccount;
import com.ecommerce.customer.accounts.domain.ports.AccountFactory;
import com.ecommerce.customer.accounts.domain.ports.UUIDGenerator;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE)
@Configuration
public final class FactoryConfiguration {
	UUIDGenerator generator;
	
	@Bean
	@Profile("production")
	AccountFactory<CustomerAccount> customerAccountFactory() {
		return new CustomerAccountFactory(generator);
	}
	
	@Bean
	@Profile("development")
	UUIDGenerator nativeJavaUUIDGenerator() {
		return () -> UUID.randomUUID();
	}
}
