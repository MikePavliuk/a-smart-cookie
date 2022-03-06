package com.a_smart_cookie.util;

import com.a_smart_cookie.entity.Language;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.stream.Stream;

public class LocaleHandlerTesting {

	@ParameterizedTest
	@MethodSource("provideLanguageAndItsDefinedLocale")
	void givenLanguage_whenGetLocaleByLanguage_thenReturnLocale() {

	}

	private static Stream<Arguments> provideLanguageAndItsDefinedLocale() {
		return Stream.of(
				Arguments.of(Language.ENGLISH, Locale.UK),
				Arguments.of(Language.UKRAINIAN, Locale.forLanguageTag("uk-UA")),
				Arguments.of(null, Locale.forLanguageTag("uk-UA"))
		);
	}

}
