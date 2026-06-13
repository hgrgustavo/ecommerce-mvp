package com.ecommerce.customer.accounts.domain.account.ports;

import java.util.UUID;
import java.util.function.Supplier;

public interface UUIDGenerator extends Supplier<UUID> {}
