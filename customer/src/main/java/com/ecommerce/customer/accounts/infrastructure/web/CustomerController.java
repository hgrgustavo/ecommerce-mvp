package com.ecommerce.customer.accounts.infrastructure.web;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.status;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.customer.accounts.infrastructure.web.idempotency.Idempotent;
import com.ecommerce.customer.accounts.usecases.createaccount.CreateCustomerAccountUseCase;
import com.ecommerce.customer.accounts.usecases.createaccount.CustomerInputDTO;
import com.ecommerce.customer.accounts.usecases.createaccount.CustomerOutputDTO;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level=PRIVATE, makeFinal=true)
@RequiredArgsConstructor
public class CustomerController {
	
}