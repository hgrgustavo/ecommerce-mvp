package com.ecommerce.sales.purchase.domain.product;

record Name(String name, String alias) {
	Name {
		alias = name.substring(0, 2);
	}
}
