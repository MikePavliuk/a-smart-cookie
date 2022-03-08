package com.a_smart_cookie.util.translator.strategies;

import com.a_smart_cookie.dto.catalog.SortingDirection;
import com.a_smart_cookie.entity.Language;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortingDirectionTranslatorStrategiesTesting {

	@Test
	void givenUkrainianLanguage_whenGetTranslatorByLanguage_thenReturnUkrainianTranslation() {
		assertEquals("за зростанням",
				SortingDirectionTranslatorStrategies.getTranslatorByLanguage(Language.UKRAINIAN).translate(SortingDirection.ASC));
	}

	@Test
	void givenEnglishLanguage_whenGetTranslatorByLanguage_thenReturnEnglishTranslation() {
		assertEquals("increasing",
				SortingDirectionTranslatorStrategies.getTranslatorByLanguage(Language.ENGLISH).translate(SortingDirection.ASC));
	}

}
