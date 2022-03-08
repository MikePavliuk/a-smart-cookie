package com.a_smart_cookie.util.validation.publication;

import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.dto.admin.PublicationDto;
import com.a_smart_cookie.entity.Genre;
import com.a_smart_cookie.entity.Language;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PublicationValidatorTesting {

	@Test
	void givenValidPublicationDto_whenGetValidationResults_thenReturnMapWithTrueValues() {
		Map<Language, String> titles = new HashMap<>();
		titles.put(Language.UKRAINIAN, "Назва");
		titles.put(Language.ENGLISH, "Title");

		Map<Language, String> descriptions = new HashMap<>();
		descriptions.put(Language.UKRAINIAN, "Опис до публікації");
		descriptions.put(Language.ENGLISH, "Description");

		PublicationDto publicationDto = new PublicationDto(
				null,
				Genre.FICTION,
				BigDecimal.TEN,
				titles,
				descriptions
		);

		Map<String, Boolean> expected = new HashMap<>();
		expected.put(EntityColumn.Publication.PRICE_PER_MONTH.getName(), true);
		expected.put(EntityColumn.PublicationInfo.TITLE.getName() + "_uk", true);
		expected.put(EntityColumn.PublicationInfo.TITLE.getName() + "_en", true);
		expected.put(EntityColumn.PublicationInfo.DESCRIPTION.getName() + "_uk", true);
		expected.put(EntityColumn.PublicationInfo.DESCRIPTION.getName() + "_en", true);

		assertEquals(expected, PublicationValidator.getValidationResults(publicationDto));
	}

	@Test
	void givenNotValidPublicationDto_whenGetValidationResults_thenReturnMapWithFalseValues() {
		Map<Language, String> titles = new HashMap<>();
		titles.put(Language.ENGLISH, "Title");

		Map<Language, String> descriptions = new HashMap<>();
		descriptions.put(Language.UKRAINIAN, "Опис");
		descriptions.put(Language.ENGLISH, "Description");

		PublicationDto publicationDto = new PublicationDto(
				null,
				Genre.FICTION,
				BigDecimal.ZERO,
				titles,
				descriptions
		);

		Map<String, Boolean> expected = new HashMap<>();
		expected.put(EntityColumn.Publication.PRICE_PER_MONTH.getName(), false);
		expected.put(EntityColumn.PublicationInfo.TITLE.getName() + "_uk", false);
		expected.put(EntityColumn.PublicationInfo.TITLE.getName() + "_en", true);
		expected.put(EntityColumn.PublicationInfo.DESCRIPTION.getName() + "_uk", false);
		expected.put(EntityColumn.PublicationInfo.DESCRIPTION.getName() + "_en", true);

		assertEquals(expected, PublicationValidator.getValidationResults(publicationDto));
	}

}
