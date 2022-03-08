package com.a_smart_cookie.dto.catalog;

import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.util.StringHandler;
import com.a_smart_cookie.util.translator.Translatable;
import com.a_smart_cookie.util.translator.TranslatorContext;
import com.a_smart_cookie.util.translator.strategies.SortingDirectionTranslatorStrategies;

import java.util.Arrays;

/**
 * Holder of possible sorting directions.
 *
 */
public enum SortingDirection implements Translatable {
	DESC,
	ASC;

	/**
	 * Gets SortingDirection value by input string and returns ASC by default, if something went wrong.
	 *
	 * @param directionString String meant to be listed as value of enum.
	 * @return SortingDirection enum value by itself.
	 */
	public static SortingDirection safeFromString(final String directionString) {
		return Arrays.stream(SortingDirection.values())
				.filter(direction -> direction.name().equalsIgnoreCase(directionString))
				.findFirst()
				.orElse(ASC);
	}

	@Override
	public String getTranslatedValue(Language language) {
		return StringHandler.capitaliseFirstLetter(
				TranslatorContext.translateInto(SortingDirectionTranslatorStrategies.getTranslatorByLanguage(language), this)
		);
	}

}
