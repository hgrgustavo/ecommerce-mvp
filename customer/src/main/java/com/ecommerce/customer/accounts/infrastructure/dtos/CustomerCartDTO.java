package com.ecommerce.customer.accounts.infrastructure.dtos;

import java.io.Serializable;
import java.util.UUID;

public record CustomerCartDTO(UUID customerId, String customerName) implements Serializable {}