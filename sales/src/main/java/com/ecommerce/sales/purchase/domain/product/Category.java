package com.ecommerce.sales.purchase.domain.product;

record Category(String name, String alias) {
	Category {
		alias = name.substring(0, 2);
	}
}
