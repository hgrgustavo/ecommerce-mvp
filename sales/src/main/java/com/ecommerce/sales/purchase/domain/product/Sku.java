package com.ecommerce.sales.purchase.domain.product;

import java.util.List;
import java.util.Objects;

record Sku(String code, ProductId id) {
	Sku {
		Objects.requireNonNull(code, "The SKU code cannot be null.");
		Objects.requireNonNull(id, "The SKU must be linked to a valid product ID.");

		if (code.isBlank())
			throw new IllegalArgumentException("The SKU code cannot be empty.");
	}

	static final Sku generateSkuCode(Product product) {
		List<String> aliases = List.of(
				product.getCategory().alias(),
				product.getBrand().alias(),
				product.getName().alias()
		);

		String join = String.join("-", aliases)
				.toUpperCase()
				.trim();

		return new Sku(join, product.getId());
	}
}
