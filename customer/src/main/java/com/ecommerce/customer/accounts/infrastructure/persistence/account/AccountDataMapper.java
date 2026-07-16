package com.ecommerce.customer.accounts.infrastructure.persistence.account;

import com.ecommerce.customer.accounts.domain.types.Account;

public interface AccountDataMapper<T extends Account> {
    T toDomainObject(AccountDatabaseEntity entity);
    AccountDatabaseEntity toDatabaseEntity(T account);
}