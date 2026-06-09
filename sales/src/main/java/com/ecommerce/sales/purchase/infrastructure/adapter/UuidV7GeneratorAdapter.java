package com.ecommerce.sales.purchase.infrastructure.adapter;

import static com.fasterxml.uuid.Generators.timeBasedEpochGenerator;

import java.util.UUID;

import com.ecommerce.sales.purchase.domain.ports.UUIDGenerator;

public class UuidV7GeneratorAdapter implements UUIDGenerator {
	@Override
	public UUID nextId() {
		return timeBasedEpochGenerator().generate();
	}
}