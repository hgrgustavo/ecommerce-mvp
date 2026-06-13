package com.ecommerce.customer.accounts.infrastructure.persistence.converters;

import java.util.function.Function;

import com.ecommerce.customer.accounts.infrastructure.persistence.valueobjects.Role;
import com.ecommerce.customer.accounts.infrastructure.persistence.valueobjects.RoleType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply=true)
public final class RoleToStringConverter implements AttributeConverter<Role, String> {
	@Override
	public String convertToDatabaseColumn(Role role) {
		Function<Role, String> function = r -> {
			return r == null || r.type() == null
	            ? null
	            : r.type().name();
		};
		
		return function.apply(role);
	}

	@Override
	public Role convertToEntityAttribute(String dbData) {
		Function<String, Role> function = r -> {
			return r == null || r.isBlank()
	            ? null
	            : Role.define(RoleType.valueOf(r));
		};
	
		return function.apply(dbData);
	}

}
