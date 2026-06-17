package com.ecommerce.customer.accounts.infrastructure.persistence.jpa.entities;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import java.util.UUID;

import com.ecommerce.customer.accounts.domain.LoginCredentials;
import com.ecommerce.customer.accounts.domain.Name;
import com.ecommerce.customer.accounts.domain.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@FieldDefaults(level = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public final class AccountDatabaseEntity {
	@Id
	UUID uuid;
	
	@Column(nullable = false)
	Role role;
	
	@Column(nullable = false)
	Name name;
	
	@Column(nullable = false)
	LoginCredentials credentials;
}