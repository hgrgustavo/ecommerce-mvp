package com.ecommerce.sales.purchase.domain.product;

record Image(String url, String altText, Integer displayOrder) {
	    Image {
	        if (url == null || url.isBlank())
	            throw new IllegalArgumentException("The image URL cannot be empty.");
	     
	        if (altText == null || altText.isBlank())
	            throw new IllegalArgumentException("Alternative text (alt) is required for accessibility.");
	    }
	}