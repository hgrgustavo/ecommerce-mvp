package com.ecommerce.customer.accounts.infrastructure.web.idempotency;

import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@Service
@FieldDefaults(level=PRIVATE, makeFinal=true)
@RequiredArgsConstructor
@Slf4j
public class IdempotencyCacheService {
	IdempotencyRepository repository;
	ObjectMapper mapper;

	public Object executeWithIdempotency(String keyFromRequest, Class<?> responseType, Supplier<Object> execution) 
			throws IdempotencyConflictException {
        final var idempotencyKey = IdempotencyKey.of(keyFromRequest);

        if (!repository.tryLock(idempotencyKey)) {
            log.warn("Duplicate request detected for key: {}", keyFromRequest);
            throw new IdempotencyConflictException("Request already in process or processed.");
        }

        try {
            Optional<String> cachedResponse = repository.getResponse(idempotencyKey);
            
            if (cachedResponse.isPresent()) {
                return deserialize(cachedResponse.get(), responseType);
            }

            Object result = execution.get();

            repository.saveResponse(idempotencyKey, serialize(result));

            return result;
        } catch (Exception e) {
            repository.unlock(idempotencyKey);
            throw e;
        }
    }

	private String serialize(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing response for idempotency.", e);
        }
    }

    private <T> T deserialize(String json, Class<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing response from idempotency cache.", e);
        }
    }
}