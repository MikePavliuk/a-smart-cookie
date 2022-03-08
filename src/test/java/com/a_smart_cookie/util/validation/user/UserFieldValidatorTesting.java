package com.a_smart_cookie.util.validation.user;

import com.a_smart_cookie.dao.EntityColumn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserFieldValidatorTesting {

	@Test
	void givenFirstNameFieldAndValidValue_whenGetValidatorByFieldName_thenReturnTrue() {
		assertTrue(UserFieldValidator.getValidatorByFieldName(EntityColumn.UserDetail.NAME.getName()).isValid("Mike"));
	}

	@Test
	void givenFirstNameFieldAndNotValidValue_whenGetValidatorByFieldName_thenReturnFalse() {
		assertFalse(UserFieldValidator.getValidatorByFieldName(EntityColumn.UserDetail.NAME.getName()).isValid("mike"));
	}

}
