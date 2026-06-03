package com.ecommerce.sales.domain.product.valueobject;

public record Image(String url, String altText, Integer displayOrder) {
	    public Image {
	        if (url == null || url.isBlank())
	            throw new IllegalArgumentException("The image URL cannot be empty.");
	     
	        if (altText == null || altText.isBlank())
	            throw new IllegalArgumentException("Alternative text (alt) is required for accessibility.");
	    }
	}