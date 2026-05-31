package com.ecommerce.sales.domain;

record Brand(String name, String alias) {
	Brand {
		alias = name.substring(0, 2);
	}
}
