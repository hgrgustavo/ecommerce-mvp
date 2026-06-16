package com.ecommerce.customer.accounts.domain.ports;

import java.util.UUID;
import java.util.function.Supplier;

public interface UUIDGenerator extends Supplier<UUID> {}
