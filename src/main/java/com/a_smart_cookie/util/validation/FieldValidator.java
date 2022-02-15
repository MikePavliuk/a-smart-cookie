package com.a_smart_cookie.util.validation;

import com.a_smart_cookie.dao.EntityColumn;

import java.util.HashMap;
import java.util.Map;

/**
 * Holder of validators that returns validator by its defined name.
 *
 */
public final class FieldValidator {

	private static final Map<String, Validator<String, Boolean>> validatorMap;

	static {
		validatorMap = new HashMap<>();

		validatorMap.put(EntityColumn.UserDetail.NAME.getName(), (name) -> {
			if (name != null) {
				return name.matches(ValidationPattern.NAME.getPattern());
			}
			return false;
		});

		validatorMap.put(EntityColumn.UserDetail.SURNAME.getName(), (surname) -> {
			if (surname != null) {
				return surname.matches(ValidationPattern.SURNAME.getPattern());
			}
			return false;
		});

		validatorMap.put(EntityColumn.User.EMAIL.getName(), (email) -> {
			if (email != null) {
				return email.matches(ValidationPattern.EMAIL.getPattern());
			}
			return false;
		});

		validatorMap.put(EntityColumn.User.PASSWORD.getName(), (password) -> {
			if (password != null) {
				return password.matches(ValidationPattern.PASSWORD.getPattern());
			}
			return false;
		});
	}

	/**
	 * Gets validator by field name.
	 *
	 * @param fieldName Name of field for validating.
	 * @return Requested validator.
	 */
	public static Validator<String, Boolean> getValidatorByFieldName(String fieldName) {
		return validatorMap.get(fieldName);
	}

}
