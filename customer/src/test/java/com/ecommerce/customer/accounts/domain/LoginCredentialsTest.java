package com.ecommerce.customer.accounts.domain;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public final class LoginCredentialsTest {
	@ParameterizedTest
    @MethodSource("parametersFactory")
    void shouldThrowExceptionWhenCredentialsAreInvalid(String invalidEmail, String invalidPassword) {
		assertThrowsExactly(IllegalArgumentException.class,
				() -> LoginCredentials.create(invalidEmail, invalidPassword));
	}
	
	private static Stream<Arguments> parametersFactory() {
		return Stream.of(
			Arguments.of("dumb#email.abc", "strongpassword"),
			Arguments.of("", ""),
			Arguments.of(" ", " ")
		);
	}
}