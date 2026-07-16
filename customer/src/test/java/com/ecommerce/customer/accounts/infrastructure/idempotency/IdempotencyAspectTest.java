package com.ecommerce.customer.accounts.infrastructure.idempotency;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.function.Supplier;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ecommerce.customer.accounts.infrastructure.web.idempotency.IdempotencyAspect;
import com.ecommerce.customer.accounts.infrastructure.web.idempotency.IdempotencyCacheService;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
class IdempotencyAspectTest {

    @Mock
    IdempotencyCacheService cacheService;

    @Mock
    ProceedingJoinPoint joinPoint;

    @Mock
    MethodSignature methodSignature;

    @Mock
    HttpServletRequest request;

    @InjectMocks
    IdempotencyAspect idempotencyAspect;

    @BeforeEach
    void setUp() {
        ServletRequestAttributes attributes = mock(ServletRequestAttributes.class);
        when(attributes.getRequest()).thenReturn(request);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @AfterEach
    void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    @DisplayName("It must intercept the request, extract the key, and pass execution to the idempotency service.")
    void shouldEnforceIdempotencySuccessfully() throws Throwable {
        String expectedKey = "uuid-idempotency-key-123";
        Class<?> expectedReturnType = String.class;
        String expectedResult = "success";

        when(request.getHeader("Idempotency-Key")).thenReturn(expectedKey);
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getReturnType()).thenReturn(expectedReturnType);
        when(cacheService.executeWithIdempotency(eq(expectedKey), eq(expectedReturnType), any()))
                .thenAnswer(invocation -> {
                    var supplier = (Supplier<?>) invocation.getArgument(2);
                    return supplier.get();
                });
        when(joinPoint.proceed()).thenReturn(expectedResult);
        
        Object actualResult = idempotencyAspect.enforceIdempotency(joinPoint);
        assertSame(expectedResult, actualResult, "The final result must be the same as the one returned by the original flow.");
        verify(joinPoint, times(1)).proceed();
        verify(cacheService, times(1)).executeWithIdempotency(eq(expectedKey), eq(expectedReturnType), any());
    }
}