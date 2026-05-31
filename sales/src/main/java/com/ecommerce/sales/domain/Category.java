package com.ecommerce.sales.domain;

record Category(String name, String alias) {
	Category {
		alias = name.substring(0, 2);
	}
}
