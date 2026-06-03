package com.ecommerce.sales.domain.product.valueobject;

public record Brand(String name, String alias) {
	public Brand {
		alias = name.substring(0, 2);
	}
}
