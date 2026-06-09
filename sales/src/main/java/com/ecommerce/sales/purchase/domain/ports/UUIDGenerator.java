package com.ecommerce.sales.purchase.domain.ports;

import java.util.UUID;

public interface UUIDGenerator {
	UUID nextId();
}