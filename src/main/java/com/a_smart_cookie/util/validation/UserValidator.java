package com.a_smart_cookie.util.validation;

import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.dto.sign_up.UserSignUpDto;

import java.util.HashMap;
import java.util.Map;

/**
 * Util method that returns validation results by dto.
 *
 */
public final class UserValidator {

	/**
	 * Gets validation result by UserSignUpDto
	 *
	 * @param user UserSignUpDto
	 * @return Map with string key - name of field and boolean value - is valid answer.
	 */
	public static Map<String, Boolean> getValidationResults(UserSignUpDto user) {
		Map<String, Boolean> validationResult = new HashMap<>();

		validationResult.put(
				EntityColumn.UserDetail.NAME.getName(),
				FieldValidator.getValidatorByFieldName(EntityColumn.UserDetail.NAME.getName()).isValid(user.getFirstName()));

		validationResult.put(
				EntityColumn.UserDetail.SURNAME.getName(),
				FieldValidator.getValidatorByFieldName(EntityColumn.UserDetail.SURNAME.getName()).isValid(user.getLastName()));

		putValidationResultsOfEmailAndPasswordFields(user.getEmail(), user.getPassword(), validationResult);

		return validationResult;
	}

	/**
	 * Gets validation result by email and password
	 *
	 *
	 * @param email User email
	 * @param password User password
	 * @return Map with string key - name of field and boolean value - is valid answer.
	 */
	public static Map<String, Boolean> getValidationResults(String email, String password) {
		Map<String, Boolean> validationResult = new HashMap<>();

		putValidationResultsOfEmailAndPasswordFields(email, password, validationResult);

		return validationResult;
	}

	private static void putValidationResultsOfEmailAndPasswordFields(
			String email,
			String password,
			Map<String, Boolean> validationResult) {
		validationResult.put(
				EntityColumn.User.EMAIL.getName(),
				FieldValidator.getValidatorByFieldName(EntityColumn.User.EMAIL.getName()).isValid(email));

		validationResult.put(
				EntityColumn.User.PASSWORD.getName(),
				FieldValidator.getValidatorByFieldName(EntityColumn.User.PASSWORD.getName()).isValid(password));
	}

	private UserValidator() {
	}

}
