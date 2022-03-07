package com.a_smart_cookie.util.translator.strategies;

import com.a_smart_cookie.dto.catalog.SortingParameter;
import com.a_smart_cookie.entity.Language;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortingParameterTranslatorStrategiesTesting {

	@Test
	void givenUkrainianLanguage_whenGetTranslatorByLanguage_thenReturnUkrainianTranslation() {
		assertEquals("назва",
				SortingParameterTranslatorStrategies.getTranslatorByLanguage(Language.UKRAINIAN).translate(SortingParameter.TITLE));
	}

	@Test
	void givenEnglishLanguage_whenGetTranslatorByLanguage_thenReturnEnglishTranslation() {
		assertEquals("title",
				SortingParameterTranslatorStrategies.getTranslatorByLanguage(Language.ENGLISH).translate(SortingParameter.TITLE));
	}

}
