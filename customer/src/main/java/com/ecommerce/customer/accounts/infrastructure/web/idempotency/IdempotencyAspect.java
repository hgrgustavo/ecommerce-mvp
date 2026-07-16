package com.ecommerce.customer.accounts.infrastructure.web.idempotency;

import static lombok.AccessLevel.PRIVATE;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Aspect
@FieldDefaults(level=PRIVATE, makeFinal=true)
@RequiredArgsConstructor
public class IdempotencyAspect {
	IdempotencyCacheService service;

	@Around("@annotation(com.ecommerce.customer.accounts.infrastructure.web.idempotency.Idempotent)")
	public Object enforceIdempotency(ProceedingJoinPoint joinPoint) throws Throwable {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		HttpServletResponse response = attributes.getResponse();
		final String keyFromRequest = request.getHeader("Idempotency-Key");
		final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		final Class<?> returnType = signature.getReturnType();

		final Object result = service.executeWithIdempotency(
				keyFromRequest,
				returnType,
				() -> {
					try {
						return joinPoint.proceed();
					} catch(Throwable e) {
						throw new RuntimeException(e);
					}
				});

		if (result instanceof ResponseEntity<?> responseEntity 
				&& attributes != null 
				&& response != null 
				&& keyFromRequest != null) {
			return ResponseEntity
					.status(responseEntity.getStatusCode())
					.headers(responseEntity.getHeaders())
					.header("Idempotency-Key", keyFromRequest)
					.body(responseEntity.getBody());
		}

		return result;
	}
}