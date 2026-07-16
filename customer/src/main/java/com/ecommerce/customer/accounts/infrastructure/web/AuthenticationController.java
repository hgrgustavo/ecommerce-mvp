package com.ecommerce.customer.accounts.infrastructure.web;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.status;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.customer.accounts.infrastructure.web.idempotency.Idempotent;
import com.ecommerce.customer.accounts.usecases.createaccount.CreateCustomerAccountUseCase;
import com.ecommerce.customer.accounts.usecases.createaccount.CustomerInputDTO;
import com.ecommerce.customer.accounts.usecases.createaccount.CustomerOutputDTO;
import com.ecommerce.customer.accounts.usecases.login.CustomerLoginInputDTO;
import com.ecommerce.customer.accounts.usecases.login.CustomerLoginOutputDTO;
import com.ecommerce.customer.accounts.usecases.login.JwtService;
import com.ecommerce.customer.accounts.usecases.login.LoginACustomerUseCase;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level=PRIVATE, makeFinal=true)
@RequiredArgsConstructor
public class AuthenticationController {
	LoginACustomerUseCase login;
	JwtService generator;
	CreateCustomerAccountUseCase create;
    
    @Idempotent
    @PostMapping(
        headers="Idempotency-Key",
        consumes=APPLICATION_JSON_VALUE,
        produces=APPLICATION_JSON_VALUE,
        path="/register"
    )
    public ResponseEntity<CustomerOutputDTO> createACustomerAccount(@RequestBody CustomerInputDTO input) {
    	CustomerOutputDTO output = create.execute(input);
        return status(CREATED).body(output);
    }
	
	@Idempotent
    @PostMapping(
        headers={ "Idempotency-Key" },
        consumes=APPLICATION_JSON_VALUE,
        produces=APPLICATION_JSON_VALUE,
        path="/login"
    )
	public ResponseEntity<CustomerLoginOutputDTO> loginACustomerAccount(
			@RequestBody CustomerLoginInputDTO input) {
		CustomerLoginOutputDTO output = login.execute(input);
		UUID uuid = output.uuid();
		final String email = output.email();
		final String jwt = generator.generate(uuid, email);
		return ResponseEntity
				.status(CREATED)
				.headers(h -> h.add(AUTHORIZATION, jwt))
				.body(output);
	}
}