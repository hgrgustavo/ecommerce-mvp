package com.ecommerce.customer.accounts.infrastructure.adapters;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.ecommerce.customer.accounts.domain.Account;
import com.ecommerce.customer.accounts.domain.ports.AccountRepository;
import com.ecommerce.customer.accounts.infrastructure.mappers.AccountDatabaseMapper;
import com.ecommerce.customer.accounts.infrastructure.persistence.jpa.entities.AccountDatabaseEntity;
import com.ecommerce.customer.accounts.infrastructure.persistence.jpa.repositories.SpringDataAccountRepository;

@Component
final class AccountRepositoryAdapter implements AccountRepository {
	private final SpringDataAccountRepository repository;
	private final AccountDatabaseMapper mapper;
	
	AccountRepositoryAdapter(SpringDataAccountRepository repository, AccountDatabaseMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public Optional<Account> findById(UUID uuid) {
		return repository.findById(uuid)
				.map(mapper::toDomainObject);
	}

	@Override
	public void save(Account account) {
		final AccountDatabaseEntity databaseEntity = mapper.toDatabaseEntity(account);
		repository.save(databaseEntity);
	}
}