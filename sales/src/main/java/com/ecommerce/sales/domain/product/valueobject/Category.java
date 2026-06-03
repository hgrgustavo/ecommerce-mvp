package com.ecommerce.sales.domain.product.valueobject;

public record Category(String name, String alias) {
	public Category {
		alias = name.substring(0, 2);
	}
}
