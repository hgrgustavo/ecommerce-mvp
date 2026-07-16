package com.ecommerce.customer.accounts.usecases.createaccount;

import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;
import java.util.UUID;

import com.ecommerce.customer.accounts.domain.CustomerAccountRepository;
import com.ecommerce.customer.accounts.domain.types.CustomerAccount;
import com.ecommerce.customer.accounts.infrastructure.persistence.account.AccountDatabaseEntity;
import com.ecommerce.customer.accounts.infrastructure.persistence.account.CustomerAccountDataMapper;
import com.ecommerce.customer.accounts.infrastructure.persistence.account.SpringDataAccountRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public final class CustomerAccountRepositoryImpl implements CustomerAccountRepository {
	SpringDataAccountRepository repository;
	CustomerAccountDataMapper mapper;

	@Override
	public Optional<CustomerAccount> findById(UUID uuid) {
		return repository.findById(uuid)
				.map(mapper::toDomainObject);
	}

	@Override
	public void save(CustomerAccount account) {
		final AccountDatabaseEntity databaseEntity = mapper.toDatabaseEntity(account);
		repository.save(databaseEntity);
	}

	@Override
	public Optional<CustomerAccount> findByEmail(String email) {
	    return repository.findByLoginCredentialsEmail(email)
	            .map(mapper::toDomainObject);
	}
}