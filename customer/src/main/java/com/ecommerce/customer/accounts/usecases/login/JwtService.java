package com.ecommerce.customer.accounts.usecases.login;

import java.util.UUID;

public interface JwtService {
	String generate(UUID accountId, String accountEmail);
}
