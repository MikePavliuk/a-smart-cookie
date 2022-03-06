package com.a_smart_cookie.tag;

import com.a_smart_cookie.entity.Language;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LocalizedDateTagTesting {

	@Test
	void givenNullLocalDate_whenConvert_thenThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class,
				() -> LocalizedDateTag.convert(null, Language.UKRAINIAN));
	}

	@Test
	void givenNullLanguage_whenConvert_thenThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class,
				() -> LocalizedDateTag.convert(LocalDate.now(), null));
	}

	@ParameterizedTest
	@MethodSource("provideTestingExamples")
	void givenValidLocalDateAndLanguage_whenConvert_thenReturnExpectedResult(LocalDate localDate, Language language, String expectedResult) {
		String actual = LocalizedDateTag.convert(localDate, language);

		assertEquals(expectedResult, actual);
	}


	private static Stream<Arguments> provideTestingExamples() {
		return Stream.of(
				Arguments.of(LocalDate.of(2022, 3, 3), Language.UKRAINIAN, "03.03.22"),
				Arguments.of(LocalDate.of(2022, 3, 3), Language.ENGLISH, "03/03/2022")
		);
	}

}
