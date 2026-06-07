package com.ecommerce.sales.purchase.domain.product;

record Brand(String name, String alias) {
	Brand {
		alias = name.substring(0, 2);
	}
}
