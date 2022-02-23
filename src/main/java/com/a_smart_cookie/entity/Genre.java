package com.a_smart_cookie.entity;

import com.a_smart_cookie.util.StringHandler;
import com.a_smart_cookie.util.translator.Translatable;
import com.a_smart_cookie.util.translator.TranslatorContext;
import com.a_smart_cookie.util.translator.strategies.GenreTranslatorStrategies;

import java.util.Arrays;

/**
 * Language entity with ability to be translated.
 */
public enum Genre implements Translatable {
	FICTION,
	NOVEL,
	COOKBOOK,
	DETECTIVE,
	HISTORICAL,
	HORROR,
	SCIENCE;

	public static Genre safeFromString(String genreName) {
		return Arrays.stream(Genre.values())
				.filter(genre -> genre.name().equalsIgnoreCase(genreName))
				.findFirst()
				.orElse(FICTION);
	}

	public static Genre fromString(String genreName) {
		return Arrays.stream(Genre.values())
				.filter(genre -> genre.name().equalsIgnoreCase(genreName))
				.findFirst()
				.orElse(null);
	}

	@Override
	public String getTranslatedValue(Language language) {
		return StringHandler.capitaliseFirstLetter(
				TranslatorContext.translateInto(GenreTranslatorStrategies.getTranslatorByLanguage(language), this)
		);
	}
}
