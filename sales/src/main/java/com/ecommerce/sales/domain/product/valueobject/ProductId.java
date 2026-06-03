package com.ecommerce.sales.domain.product.valueobject;

public record ProductId(byte id) {
	public ProductId {
		if(id <= 0)
			throw new IllegalArgumentException("The Product ID must be a positive integer.");
	}
}