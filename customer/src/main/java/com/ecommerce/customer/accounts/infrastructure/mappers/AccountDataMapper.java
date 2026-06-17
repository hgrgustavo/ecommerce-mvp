package com.ecommerce.customer.accounts.infrastructure.mappers;

import org.springframework.stereotype.Component;

import com.ecommerce.customer.accounts.domain.Account;
import com.ecommerce.customer.accounts.infrastructure.persistence.jpa.entities.AccountDatabaseEntity;

@Component
public sealed abstract class AccountDataMapper<T extends Account> permits CustomerAccountDataMapper {
	abstract T toDomainObject(AccountDatabaseEntity entity);
	abstract AccountDatabaseEntity toDatabaseEntity(T account);
}