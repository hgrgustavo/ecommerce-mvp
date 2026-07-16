package com.ecommerce.customer.accounts.infrastructure.persistence.account;

import static com.ecommerce.customer.accounts.infrastructure.persistence.account.AccountDatabaseEntity.builder;
import static lombok.AccessLevel.PRIVATE;

import com.ecommerce.customer.accounts.domain.CustomerAccountFactory;
import com.ecommerce.customer.accounts.domain.types.CustomerAccount;
import com.ecommerce.customer.accounts.infrastructure.persistence.account.embeddables.LoginCredentialsEmbeddable;
import com.ecommerce.customer.accounts.infrastructure.persistence.account.embeddables.NameEmbeddable;
import com.ecommerce.customer.accounts.infrastructure.persistence.account.embeddables.RoleEmbeddable;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=PRIVATE, makeFinal=true)
@RequiredArgsConstructor
public class CustomerAccountDataMapperImpl implements CustomerAccountDataMapper {
	CustomerAccountFactory factory;

	@Override
	public CustomerAccount toDomainObject(AccountDatabaseEntity entity) {
		return factory.create(
				entity.getUuid(), 
				entity.getName().value(), 
				new String[] { entity.getCredentials().email(), entity.getCredentials().password() }
		);
	}

	@Override
	public AccountDatabaseEntity toDatabaseEntity(CustomerAccount account) {
		return builder()
				.uuid(account.getUuid())
				.name(NameEmbeddable.create(account.getName().value()))
				.credentials(LoginCredentialsEmbeddable.create(
						account.getCredentials().email(), 
						account.getCredentials().password()))
				.role(RoleEmbeddable.define(account.getRole().type()))
				.build();
	}
}
