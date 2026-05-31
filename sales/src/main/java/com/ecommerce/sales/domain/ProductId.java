package com.ecommerce.sales.domain;

record ProductId(byte id) {
	ProductId {
		if(id <= 0)
			throw new 
			IllegalArgumentException("The Product ID must be a positive integer.");
	}
}
