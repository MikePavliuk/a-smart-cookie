package com.a_smart_cookie.util.validation.user;

import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.util.validation.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * Holder of validators that returns validator by its defined name.
 *
 */
public final class UserFieldValidator {

	private static final Map<String, Validator<String, Boolean>> validatorMap;

	static {
		validatorMap = new HashMap<>();

		validatorMap.put(EntityColumn.UserDetail.NAME.getName(), (name) -> {
			if (name != null) {
				return name.matches(UserValidationPattern.NAME.getPattern());
			}
			return false;
		});

		validatorMap.put(EntityColumn.UserDetail.SURNAME.getName(), (surname) -> {
			if (surname != null) {
				return surname.matches(UserValidationPattern.SURNAME.getPattern());
			}
			return false;
		});

		validatorMap.put(EntityColumn.User.EMAIL.getName(), (email) -> {
			if (email != null) {
				return email.matches(UserValidationPattern.EMAIL.getPattern());
			}
			return false;
		});

		validatorMap.put(EntityColumn.User.PASSWORD.getName(), (password) -> {
			if (password != null) {
				return password.matches(UserValidationPattern.PASSWORD.getPattern());
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
