package com.ecommerce.customer.accounts.infrastructure.web.idempotency;

import static java.lang.Boolean.TRUE;
import static java.util.Optional.of;
import static lombok.AccessLevel.PRIVATE;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class RedisIdempotencyRepository implements IdempotencyRepository {
	StringRedisTemplate template;
	static String PREFIX = "idempotency:account:";

	@Override
	public boolean tryLock(IdempotencyKey key) {
		final Boolean success = template.opsForValue()
				.setIfAbsent(
						PREFIX + key.value(),
						Status.PROCESSING.name(),
						Duration.ofHours(1)
						);
		return TRUE.equals(success);
	}

	@Override
	public void unlock(IdempotencyKey key) {
		template.delete(PREFIX + key.value());
	}

	@Override
	public void saveResponse(IdempotencyKey key, String responseBody) {
		template.opsForValue()
		.set(
				PREFIX + key.value(), 
				responseBody,
				Duration.ofHours(1)
				);
	}

	@Override
	public Optional<String> getResponse(IdempotencyKey key) {
		return of(
				template.opsForValue()
					.get(PREFIX + key.value()
						)
				);
	}
}
