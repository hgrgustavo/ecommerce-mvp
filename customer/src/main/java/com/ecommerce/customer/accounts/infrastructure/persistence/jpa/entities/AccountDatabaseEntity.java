package com.ecommerce.customer.accounts.infrastructure.persistence.jpa.entities;

import static lombok.AccessLevel.PROTECTED;

import java.util.UUID;

import com.ecommerce.customer.accounts.domain.LoginCredentials;
import com.ecommerce.customer.accounts.infrastructure.persistence.valueobjects.Name;
import com.ecommerce.customer.accounts.infrastructure.persistence.valueobjects.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
public final class AccountDatabaseEntity {
	@Id
	private UUID uuid;
	
	@Column(nullable = false)
	private Role role;
	
	@Column(nullable = false)
	private Name name;
	
	@Column(nullable = false)
	private LoginCredentials credentials;
}