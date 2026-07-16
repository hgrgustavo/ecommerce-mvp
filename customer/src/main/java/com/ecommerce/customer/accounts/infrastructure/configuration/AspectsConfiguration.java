package com.ecommerce.customer.accounts.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;

import com.ecommerce.customer.accounts.infrastructure.web.idempotency.IdempotencyAspect;
import com.ecommerce.customer.accounts.infrastructure.web.idempotency.IdempotencyCacheService;

@Configuration
@EnableAspectJAutoProxy
public class AspectsConfiguration {
	@Bean
	IdempotencyAspect idempotencyAspect(@Lazy IdempotencyCacheService cacheService) {
		return new IdempotencyAspect(cacheService);
	}
}