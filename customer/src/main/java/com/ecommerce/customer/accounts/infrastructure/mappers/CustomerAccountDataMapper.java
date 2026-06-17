package com.ecommerce.customer.accounts.infrastructure.mappers;

import static com.ecommerce.customer.accounts.infrastructure.persistence.jpa.entities.AccountDatabaseEntity.builder;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.ecommerce.customer.accounts.domain.CustomerAccount;
import com.ecommerce.customer.accounts.domain.ports.AccountFactory;
import com.ecommerce.customer.accounts.infrastructure.persistence.jpa.entities.AccountDatabaseEntity;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor(access = PROTECTED)
public final class CustomerAccountDataMapper extends AccountDataMapper<CustomerAccount> {
	AccountFactory<CustomerAccount> factory;

	@Override
	public CustomerAccount toDomainObject(AccountDatabaseEntity entity) {
		return factory.recreate(
				entity.getUuid(), 
				entity.getName(), 
				entity.getCredentials()
		);
	}

	@Override
	public AccountDatabaseEntity toDatabaseEntity(CustomerAccount account) {
		return builder()
				.uuid(account.getUuid())
				.role(account.getRole())
				.name(account.getName())
				.credentials(account.getCredentials())
				.build();
	}
}
