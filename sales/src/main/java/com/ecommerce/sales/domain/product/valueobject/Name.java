package com.ecommerce.sales.domain.product.valueobject;

public record Name(String name, String alias) {
	public Name {
		alias = name.substring(0, 2);
	}
}
