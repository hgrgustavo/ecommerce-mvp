package com.ecommerce.sales.domain;

record Name(String name, String alias) {
	Name {
		alias = name.substring(0, 2);
	}
}
