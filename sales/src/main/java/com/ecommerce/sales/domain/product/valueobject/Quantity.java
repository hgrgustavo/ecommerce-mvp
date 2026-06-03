package com.ecommerce.sales.domain.product.valueobject;

public record Quantity(int value) implements Comparable<Quantity> {
	private static final byte MAX_ALLOWED = 50;
	
	public Quantity {
        if (value < 0)
            throw new IllegalArgumentException("The quantity cannot be negative.");
        
        if (value > MAX_ALLOWED)
            throw new IllegalArgumentException("The quantity cannot exceed " + MAX_ALLOWED);
    }
	
	public Quantity add(int other) {
		return new Quantity(this.value + other);
	}
	
	public Quantity subtract(int other) {
		return new Quantity(this.value - other);
	}

	@Override
	public int compareTo(Quantity other) {
		return Integer.compare(this.value, other.value());
	}
}