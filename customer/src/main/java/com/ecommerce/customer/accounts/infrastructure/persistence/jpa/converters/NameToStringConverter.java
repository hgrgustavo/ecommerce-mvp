package com.ecommerce.customer.accounts.infrastructure.persistence.jpa.converters;

import java.util.function.Function;

import com.ecommerce.customer.accounts.domain.Name;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply=true)
public final class NameToStringConverter implements AttributeConverter<Name, String> {
	@Override
	public String convertToDatabaseColumn(Name name) {
		Function<Name, String> function = n -> {
			return n.value() == null || n.value().isBlank()
		            ? null
		            : n.value();
		};
		
		return function.apply(name);
	}

	@Override
	public Name convertToEntityAttribute(String dbData) {
		Function<String, Name> function = s -> {
			return s == null || s.isBlank()
		            ? null
		            : Name.create(s);
		};
		
		return function.apply(dbData);
	}
}
