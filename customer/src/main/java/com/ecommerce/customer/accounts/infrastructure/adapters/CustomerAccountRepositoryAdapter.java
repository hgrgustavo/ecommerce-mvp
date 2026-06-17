package com.ecommerce.customer.accounts.infrastructure.adapters;

import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.ecommerce.customer.accounts.domain.CustomerAccount;
import com.ecommerce.customer.accounts.domain.ports.AccountRepository;
import com.ecommerce.customer.accounts.infrastructure.mappers.CustomerAccountDataMapper;
import com.ecommerce.customer.accounts.infrastructure.persistence.jpa.entities.AccountDatabaseEntity;
import com.ecommerce.customer.accounts.infrastructure.persistence.jpa.repositories.SpringDataAccountRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Repository
final class CustomerAccountRepositoryAdapter implements AccountRepository<CustomerAccount> {
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
}