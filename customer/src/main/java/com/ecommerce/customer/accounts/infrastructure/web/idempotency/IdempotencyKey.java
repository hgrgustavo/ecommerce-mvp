package com.ecommerce.customer.accounts.infrastructure.web.idempotency;

import static java.util.regex.Pattern.compile;

import java.util.regex.Pattern;

public record IdempotencyKey(String value) {
    private final static Pattern UUID_V4_PATTERN = 
        compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}");
    
    public IdempotencyKey {
        if (value == null || !UUID_V4_PATTERN.matcher(value).matches())
            throw new IllegalArgumentException("Invalid IdempotencyKey: value must be a valid UUIDv4.");
    }
    
    public static IdempotencyKey of(String value) {
    	return new IdempotencyKey(value);
    }
}