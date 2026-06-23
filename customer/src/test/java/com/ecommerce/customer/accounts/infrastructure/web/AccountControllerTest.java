package com.ecommerce.customer.accounts.infrastructure.web;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ecommerce.customer.accounts.application.usecases.createaccount.CustomerInputDTO;

import lombok.experimental.FieldDefaults;
import tools.jackson.databind.ObjectMapper;

@FieldDefaults(level = PRIVATE)
@WebMvcTest(value = AccountController.class)
final class AccountControllerTest {
	CustomerInputDTO input;

	@Autowired
	MockMvc mvc;

	@Autowired
	ObjectMapper mapper;

	@BeforeEach
	void defineInput() {
		this.input = CustomerInputDTO.create(
				"Gustavo Henrique", 
				"gustavosvalidemail@gmail.com", 
				"$strongpasswordthatllbehashed$"
		);
	}

	@Test
	void customerAccountDataReceived() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/account")
				.contentType(APPLICATION_JSON)
				.content(mapper.writeValueAsString(input))
				.accept(APPLICATION_JSON))
		.andExpectAll(
				status().isCreated(), 
				jsonPath("$.name").value(input.name()), 
				jsonPath("$.email").value(input.email()), 
				jsonPath("$.password").value(input.password())
		);
	}
}