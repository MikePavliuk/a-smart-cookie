package com.a_smart_cookie.util.translator;

import com.a_smart_cookie.entity.Genre;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.util.translator.strategies.GenreTranslatorStrategies;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TranslatorContextTesting {

	@Test
	void givenTranslatorObject_whenTranslateInto_thenReturnTranslation() {
		assertEquals("фантастика",
				TranslatorContext.translateInto(GenreTranslatorStrategies.getTranslatorByLanguage(Language.UKRAINIAN), Genre.FICTION));
	}

}
