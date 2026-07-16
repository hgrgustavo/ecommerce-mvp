package com.ecommerce.customer.accounts.infrastructure.web;

import static com.ecommerce.customer.accounts.usecases.login.CustomerLoginInputDTO.create;
import static java.util.UUID.randomUUID;
import static lombok.AccessLevel.PRIVATE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ecommerce.customer.accounts.domain.credentials.LoginCredentials;
import com.ecommerce.customer.accounts.infrastructure.configuration.AspectsConfiguration;
import com.ecommerce.customer.accounts.infrastructure.configuration.SecurityConfiguration;
import com.ecommerce.customer.accounts.infrastructure.web.idempotency.IdempotencyCacheService;
import com.ecommerce.customer.accounts.usecases.createaccount.CreateCustomerAccountUseCase;
import com.ecommerce.customer.accounts.usecases.createaccount.CustomerInputDTO;
import com.ecommerce.customer.accounts.usecases.createaccount.CustomerOutputDTO;
import com.ecommerce.customer.accounts.usecases.login.CustomerLoginInputDTO;
import com.ecommerce.customer.accounts.usecases.login.CustomerLoginOutputDTO;
import com.ecommerce.customer.accounts.usecases.login.JwtService;
import com.ecommerce.customer.accounts.usecases.login.LoginACustomerUseCase;

import lombok.experimental.FieldDefaults;
import tools.jackson.databind.ObjectMapper;

@FieldDefaults(level=PRIVATE)
@WebMvcTest(controllers=AuthenticationController.class)
@Import({
	SecurityConfiguration.class,
	AspectsConfiguration.class
})
class AuthControllerTest {
	@MockitoBean
	CreateCustomerAccountUseCase create;

	@MockitoBean
	LoginACustomerUseCase login;

	@MockitoBean
	JwtService jwt;

	@Autowired
	ObjectMapper mapper;

	@MockitoBean
	IdempotencyCacheService cache;

	@Autowired
	MockMvc mvc;

	@Test
	void customerShouldLogIn() throws Exception {
		final String name = "Gustavo Henrique";
		final String email = "gustavo@email.com";
		final String password = "$V4l1dP4ssw0rd$";
		final String mockJwtToken = "header.payload.signature-fake";
		final UUID uuid = randomUUID();
		
		final CustomerLoginInputDTO input = create(LoginCredentials.create(email, password));
		final CustomerLoginOutputDTO output = CustomerLoginOutputDTO.create(uuid, name, email);

		when(cache.executeWithIdempotency(any(), any(), any()))
		.thenAnswer(invocation -> {
			Supplier<?> supplier = invocation.getArgument(2);
			return supplier.get();
		});

		when(login.execute(input))
		.thenReturn(output);

		when(jwt.generate(uuid, email))
		.thenReturn(mockJwtToken);

		mvc.perform(
				post("/auth/login")
				.contentType(APPLICATION_JSON)
				.content(mapper.writeValueAsString(input))
				.headers((h) -> {
					h.add("Idempotency-Key", uuid.toString());
				})
				.with(csrf())
				.accept(APPLICATION_JSON))
		.andExpectAll(
				status().is2xxSuccessful(),
				jsonPath("$.uuid").value(output.uuid().toString()),
				jsonPath("$.name").value(output.name()),
				jsonPath("$.email").value(output.email()),
				header().string("Idempotency-Key", uuid.toString()),
				header().string("Authorization", mockJwtToken)
				);

		verify(login).execute(input);
		verify(jwt).generate(uuid, email);
	}

	@Test
	@WithMockUser
	void customerShouldBeRegistered() throws Exception {
		final String defaultName = "Gustavo Henrique";
		final String defaultEmail = "gustavosvalidemail@gmail.com";
		final String defaultPassword = "V4l1dP4ssw0rd";
		final String uuid = randomUUID().toString();
		
		final CustomerInputDTO input = CustomerInputDTO.create(
				defaultName,
				defaultEmail,
				defaultPassword);

		final CustomerOutputDTO output = CustomerOutputDTO.create(
				defaultName,
				defaultEmail);

		when(create.execute(any(CustomerInputDTO.class)))
		.thenReturn(output);
		
		when(cache.executeWithIdempotency(any(), any(), any()))
		.thenAnswer(invocation -> {
			Supplier<?> supplier = invocation.getArgument(2);
			return supplier.get();
		});

		mvc.perform(
				post("/auth/register")
				.contentType(APPLICATION_JSON)
				.content(mapper.writeValueAsString(input))
				.headers((h) -> {
					h.add("Idempotency-Key", uuid);
				})
				.with(csrf())
				.accept(APPLICATION_JSON))
		.andExpectAll(
				status().is2xxSuccessful(),
				jsonPath("$.name").value(defaultName), 
				jsonPath("$.email").value(defaultEmail),
				header().string("Idempotency-Key", uuid)
				);
	}
}