package com.ecommerce.customer.accounts.infrastructure.persistence;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecommerce.customer.accounts.infrastructure.configuration.SecurityConfiguration;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level=PRIVATE)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SecurityConfiguration.class })
public final class PasswordHashingTest {
    static final String RAW_PASSWORD = "$trongP4ssw0rd";

    @Autowired
    PasswordEncoder encoder;

    @TestFactory
    Iterator<DynamicTest> argon2SanityTests() {
        String encodedPassword = encoder.encode(RAW_PASSWORD);

        return Arrays.asList(
            dynamicTest("Matching passwords", () -> 
                assertThat(encoder.matches(RAW_PASSWORD, encodedPassword)).isTrue()),
            dynamicTest("Unmatching passwords", () -> 
                assertThat(encoder.matches("Wr0ngP4ssw0rd", encodedPassword)).isFalse())
        ).iterator();
    }

    @TestFactory
    Iterator<DynamicTest> argon2ParametersTests() {
        String encodedPassword = encoder.encode(RAW_PASSWORD);

        return Arrays.asList(
            dynamicTest("Argon2id is being used", () -> 
                assertThat(encodedPassword).startsWith("$argon2id$")),
            dynamicTest("Parallelism check", () -> 
                assertThat(encodedPassword).contains("p=1"))
        ).iterator();
    }

    @TestFactory
    Iterator<DynamicTest> argon2PerformanceTests() {
        return Arrays.asList(
            dynamicTest("Hashing execution time", () -> {
                long startTime = System.currentTimeMillis();
                encoder.encode(RAW_PASSWORD);
                long interval = System.currentTimeMillis() - startTime;

                assertThat(interval).isBetween(100L, 600L);
            })
        ).iterator();
    }
}