package com.a_smart_cookie.util.validation.user;

import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.dto.sign_up.UserSignUpDto;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserValidatorTesting {

	@Test
	void givenValidUserSignUpDto_whenGetValidationResults_thenReturnMapWithTrueValues() {
		UserSignUpDto userSignUpDto = new UserSignUpDto(
				"Mike",
				"Pavliuk",
				"pavliuk.mykhailo.dev@gmail.com",
				"Misha22788!"
		);

		Map<String, Boolean> expected = new HashMap<>();
		expected.put(EntityColumn.UserDetail.NAME.getName(), true);
		expected.put(EntityColumn.UserDetail.SURNAME.getName(), true);
		expected.put(EntityColumn.User.EMAIL.getName(), true);
		expected.put(EntityColumn.User.PASSWORD.getName(), true);

		assertEquals(expected, UserValidator.getValidationResults(userSignUpDto));
	}

	@Test
	void givenNotValidUserSignUpDto_whenGetValidationResults_thenReturnMapWithFalseValues() {
		UserSignUpDto userSignUpDto = new UserSignUpDto(
				"mike",
				"Pavliuk",
				"pavliuk.mykhailo.dev@gmail.com",
				"password"
		);

		Map<String, Boolean> expected = new HashMap<>();
		expected.put(EntityColumn.UserDetail.NAME.getName(), false);
		expected.put(EntityColumn.UserDetail.SURNAME.getName(), true);
		expected.put(EntityColumn.User.EMAIL.getName(), true);
		expected.put(EntityColumn.User.PASSWORD.getName(), false);

		assertEquals(expected, UserValidator.getValidationResults(userSignUpDto));
	}

	@Test
	void givenValidEmailAndPassword_whenGetValidationResults_thenReturnMapWithTrueValues() {
		Map<String, Boolean> expected = new HashMap<>();
		expected.put(EntityColumn.User.EMAIL.getName(), true);
		expected.put(EntityColumn.User.PASSWORD.getName(), true);

		assertEquals(expected, UserValidator.getValidationResults("pavliuk.mykhailo.dev@gmail.com", "Misha22788!"));
	}

	@Test
	void givenNotValidEmailAndPassword_whenGetValidationResults_thenReturnMapWithFalseValues() {
		Map<String, Boolean> expected = new HashMap<>();
		expected.put(EntityColumn.User.EMAIL.getName(), false);
		expected.put(EntityColumn.User.PASSWORD.getName(), false);

		assertEquals(expected, UserValidator.getValidationResults("pavliuk.mykhailo.dev", "password!"));
	}

}
