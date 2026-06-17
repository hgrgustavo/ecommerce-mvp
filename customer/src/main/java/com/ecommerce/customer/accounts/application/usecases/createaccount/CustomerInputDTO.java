package com.ecommerce.customer.accounts.infrastructure.dtos;

import java.io.Serializable;
import java.util.UUID;

public record CustomerInputDTO(UUID uuid, String name, String email, String password) implements Serializable {}
