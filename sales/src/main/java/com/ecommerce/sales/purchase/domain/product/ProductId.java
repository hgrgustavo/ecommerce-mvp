package com.ecommerce.sales.purchase.domain.product;

record ProductId(byte id) {
	ProductId {
		if(id <= 0)
			throw new IllegalArgumentException("The Product ID must be a positive integer.");
	}
}