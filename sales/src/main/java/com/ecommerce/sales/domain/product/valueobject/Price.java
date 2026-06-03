package com.ecommerce.sales.domain.product.valueobject;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_EVEN;
import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;

public record Price(BigDecimal value) {
	public Price {
		requireNonNull(value, "The price value cannot be zero.");
		if (value.compareTo(ZERO) < 0)
			throw new IllegalArgumentException("The price cannot be negative.");
		value = value.setScale(2, HALF_EVEN);
	}

	Price add(Price other) {
		BigDecimal otherValue = other.value();
		return new Price(this.value.add(otherValue));
	}

	Price subtract(Price other) {
		BigDecimal otherValue = other.value();
		if (this.value.compareTo(otherValue) < 0)
			throw new IllegalArgumentException("The amount to be subtracted is greater than the current price.");
		return new Price(this.value.subtract(otherValue));
	}
}