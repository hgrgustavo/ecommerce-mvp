package com.ecommerce.customer.accounts.infrastructure.persistence.account;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.UuidGenerator.Style.TIME;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.ecommerce.customer.accounts.infrastructure.persistence.account.embeddables.LoginCredentialsEmbeddable;
import com.ecommerce.customer.accounts.infrastructure.persistence.account.embeddables.NameEmbeddable;
import com.ecommerce.customer.accounts.infrastructure.persistence.account.embeddables.RoleEmbeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name="accounts")
@Getter
@Setter
@Builder
@FieldDefaults(level=PRIVATE)
@NoArgsConstructor(access=PROTECTED)
@AllArgsConstructor(access=PRIVATE)
public final class AccountDatabaseEntity {
	@Id
	@GeneratedValue
	@UuidGenerator(style=TIME)
	UUID uuid;
	
	@Column(nullable = false)
	@Embedded
	RoleEmbeddable role;
	
	@Column(nullable = false)
	@Embedded
	NameEmbeddable name;
	
	@Column(nullable = false)
	@Embedded
	LoginCredentialsEmbeddable credentials;
}