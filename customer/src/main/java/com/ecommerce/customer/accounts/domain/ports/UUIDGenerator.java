package com.ecommerce.customer.accounts.domain.ports;

import java.util.UUID;
import java.util.function.Supplier;

@FunctionalInterface
public interface UUIDGenerator {
	UUID getV7();
}
