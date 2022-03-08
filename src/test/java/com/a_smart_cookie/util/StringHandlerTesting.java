package com.a_smart_cookie.util;

import com.a_smart_cookie.entity.Language;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringHandlerTesting {

	@Test
	void givenNullInput_whenCapitaliseFirstLetter_thenThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class,
				() -> StringHandler.capitaliseFirstLetter(null));
	}

	@Test
	void givenEmptyInput_whenCapitaliseFirstLetter_thenThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class,
				() -> StringHandler.capitaliseFirstLetter(""));
	}

	@ParameterizedTest
	@MethodSource("provideTestingExamples")
	void givenValidInput_whenCapitaliseFirstLetter_thenReturnInputWithCapitalisedFirstLetter() {
		assertEquals("English", StringHandler.capitaliseFirstLetter("english"));
	}

	private static Stream<Arguments> provideTestingExamples() {
		return Stream.of(
				Arguments.of(LocalDate.of(2022, 3, 3), Language.UKRAINIAN, "03.03.22"),
				Arguments.of(LocalDate.of(2022, 3, 3), Language.ENGLISH, "03/03/2022")
		);
	}

}
