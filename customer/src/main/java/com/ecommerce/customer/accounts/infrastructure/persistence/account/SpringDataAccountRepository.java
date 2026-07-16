package com.ecommerce.customer.accounts.infrastructure.persistence.account;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataAccountRepository 
	extends JpaRepository<AccountDatabaseEntity, UUID> {
    Optional<AccountDatabaseEntity> findByLoginCredentialsEmail(String email);
}
