package com.ecommerce.sales.purchase.domain.product;

import java.util.List;

public final class Product {
	private ProductId id;
	private Sku sku;
	private Category category;	
	private Brand brand;
	private Name name;
	private Quantity quantity;
	private Price price;
	private List<Image> images;
	
	ProductId getId() {
		return id;
	}
	
	Sku getSku() {
		return sku;
	}
	
	Category getCategory() {
		return category;
	}
	
	Brand getBrand() {
		return brand;
	}
	
	Name getName() {
		return name;
	}
	
	Quantity getQuantity() {
		return quantity;
	}
	
	Price getPrice() {
		return price;
	}
	
	List<Image> getImages() {
		return images;
	}
}