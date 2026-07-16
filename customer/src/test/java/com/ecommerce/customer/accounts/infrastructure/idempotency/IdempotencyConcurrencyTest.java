package com.ecommerce.customer.accounts.infrastructure.idempotency;

import static java.util.concurrent.TimeUnit.SECONDS;
import static lombok.AccessLevel.PRIVATE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.test.context.support.WithMockUser;

import com.ecommerce.customer.accounts.domain.CustomerAccountRepository;
import com.ecommerce.customer.accounts.usecases.createaccount.CustomerInputDTO;
import com.ecommerce.customer.accounts.usecases.createaccount.CustomerOutputDTO;

import lombok.experimental.FieldDefaults;

@SpringBootTest(webEnvironment=RANDOM_PORT)
@FieldDefaults(level=PRIVATE)
public class IdempotencyConcurrencyTest {
	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	CustomerAccountRepository repository;

	CustomerInputDTO dto;

	@BeforeEach
	void setUp() {
		this.dto = CustomerInputDTO.create(
				"Gustavo Henrique", 
				"gustavosvalidemail@gmail.com", 
				"$V4l1dP4ssw0rd$"
				);
	}

	@TestFactory
	@WithMockUser
	DynamicTest[] shouldAvoidRaceCondition() throws InterruptedException {
		final int numberOfThreads = 2;
		ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

		CountDownLatch startLatch = new CountDownLatch(1);
		CountDownLatch finishLatch = new CountDownLatch(numberOfThreads);

		AtomicReferenceArray<HttpStatusCode> codes = new AtomicReferenceArray<>(numberOfThreads);

		try {
			IntStream.range(0, numberOfThreads).forEach(i -> {
				executorService.submit(() -> {
					try {
						startLatch.await();
						HttpStatusCode statusCode = restTemplate
								.postForEntity("/account", dto, CustomerOutputDTO.class)
								.getStatusCode();
						codes.set(i, statusCode);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					} finally {
						finishLatch.countDown();
					}
				});
			});

			startLatch.countDown();

			if (! finishLatch.await(5, SECONDS)) {
				Assertions.fail("The test exceeded the thread timeout.");
			}
		} finally {
			executorService.shutdown();
			if (! executorService.awaitTermination(2, SECONDS)) {
				executorService.shutdownNow();
			}
		}

		final UUID uuid = dto.uuid();

		return new DynamicTest[] {
				dynamicTest(
						"Account was stored", 
						() -> assertDoesNotThrow(
								() -> repository.findById(uuid)
								.orElseThrow(() -> new AssertionError("Account not found.")), 
								"Exception thrown while locating the account."
								)
						),

				dynamicTest(
						"API blocked duplicates",
						() -> {
							final long successCount = IntStream.range(0, numberOfThreads)
									.mapToObj(codes::get)
									.filter(status -> status != null && status.is2xxSuccessful())
									.count();

							assertEquals(
									1, 
									successCount, 
									"Idempotency failed! More than one request was processed.");
						})
		};
	}
}