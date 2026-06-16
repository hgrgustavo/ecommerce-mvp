package com.ecommerce.customer.accounts.infrastructure.adapters;

import static com.fasterxml.uuid.Generators.timeBasedEpochGenerator;

import java.util.UUID;

import com.ecommerce.customer.accounts.domain.ports.UUIDGenerator;

public final class UUIDV7GeneratorAdapter implements UUIDGenerator {
	@Override
	public UUID get() {
		return timeBasedEpochGenerator().generate();
	}
}
