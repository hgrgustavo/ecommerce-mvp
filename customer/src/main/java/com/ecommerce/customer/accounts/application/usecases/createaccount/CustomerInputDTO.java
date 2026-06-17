package com.ecommerce.customer.accounts.application.usecases.createaccount;

import java.io.Serializable;
import java.util.UUID;

public record CustomerInputDTO(String name, String email, String password) implements Serializable {}
