package com.ecommerce.sales.domain;

import java.util.List;

import com.ecommerce.sales.domain.product.valueobject.Brand;
import com.ecommerce.sales.domain.product.valueobject.Category;
import com.ecommerce.sales.domain.product.valueobject.Image;
import com.ecommerce.sales.domain.product.valueobject.Name;
import com.ecommerce.sales.domain.product.valueobject.Price;
import com.ecommerce.sales.domain.product.valueobject.ProductId;
import com.ecommerce.sales.domain.product.valueobject.Quantity;
import com.ecommerce.sales.domain.product.valueobject.Sku;

public final class Product {
	private ProductId id;
	private Sku sku;
	private Category category;	
	private Brand brand;
	private Name name;
	private Quantity quantity;
	private Price price;
	private List<Image> images;
	
	public ProductId getId() {
		return id;
	}
	
	public Category getCategory() {
		return category;
	}

	public Brand getBrand() {
		return brand;
	}
	
	public Name getName() {
		return name;
	}
}