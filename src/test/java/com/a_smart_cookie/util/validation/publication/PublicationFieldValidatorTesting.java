package com.a_smart_cookie.util.validation.publication;

import com.a_smart_cookie.dao.EntityColumn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PublicationFieldValidatorTesting {

	@Test
	void givenPublicationTitleFieldAndValidValue_whenGetValidatorByFieldName_thenReturnTrue() {
		assertTrue(PublicationFieldValidator.getValidatorByFieldName(EntityColumn.PublicationInfo.TITLE.getName()).isValid("Title"));
	}

	@Test
	void givenPublicationTitleFieldAndNotValidValue_whenGetValidatorByFieldName_thenReturnFalse() {
		assertFalse(PublicationFieldValidator.getValidatorByFieldName(EntityColumn.PublicationInfo.TITLE.getName()).isValid("t"));
	}

}
