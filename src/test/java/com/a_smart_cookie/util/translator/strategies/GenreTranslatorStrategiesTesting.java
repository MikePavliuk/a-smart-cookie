package com.a_smart_cookie.util.translator.strategies;

import com.a_smart_cookie.entity.Genre;
import com.a_smart_cookie.entity.Language;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenreTranslatorStrategiesTesting {

	@Test
	void givenUkrainianLanguage_whenGetTranslatorByLanguage_thenReturnUkrainianTranslation() {
		assertEquals("фантастика",
				GenreTranslatorStrategies.getTranslatorByLanguage(Language.UKRAINIAN).translate(Genre.FICTION));
	}

	@Test
	void givenEnglishLanguage_whenGetTranslatorByLanguage_thenReturnEnglishTranslation() {
		assertEquals("fiction",
				GenreTranslatorStrategies.getTranslatorByLanguage(Language.ENGLISH).translate(Genre.FICTION));
	}

}
