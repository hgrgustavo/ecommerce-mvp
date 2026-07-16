package com.ecommerce.customer.accounts.domain;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.ecommerce.customer.accounts.domain.credentials.Name;

class NameTest {
    @ParameterizedTest
    @ValueSource(strings = {"1nv4l1dN*m3", "", " ", "A@b"})
    void shouldThrowExceptionWhenNameIsInvalid(String invalidName) {
        assertThrowsExactly(IllegalArgumentException.class, 
            () -> Name.create(invalidName)
        );
    }
}