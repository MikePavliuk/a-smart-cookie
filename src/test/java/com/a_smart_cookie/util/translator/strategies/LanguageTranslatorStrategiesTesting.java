package com.a_smart_cookie.util.translator.strategies;

import com.a_smart_cookie.entity.Language;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LanguageTranslatorStrategiesTesting {

	@Test
	void givenUkrainianLanguage_whenGetTranslatorByLanguage_thenReturnUkrainianTranslation() {
		assertEquals("українська",
				LanguageTranslatorStrategies.getTranslatorByLanguage(Language.UKRAINIAN).translate(Language.UKRAINIAN));
	}

	@Test
	void givenEnglishLanguage_whenGetTranslatorByLanguage_thenReturnEnglishTranslation() {
		assertEquals("ukrainian",
				LanguageTranslatorStrategies.getTranslatorByLanguage(Language.ENGLISH).translate(Language.UKRAINIAN));
	}

}
