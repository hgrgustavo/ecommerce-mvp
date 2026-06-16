package com.ecommerce.customer.accounts.infrastructure.persistence.jpa.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.customer.accounts.infrastructure.persistence.jpa.entities.AccountDatabaseEntity;

public interface SpringDataAccountRepository extends JpaRepository<AccountDatabaseEntity, UUID> {}
