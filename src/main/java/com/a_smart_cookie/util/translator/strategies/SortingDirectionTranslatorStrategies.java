package com.a_smart_cookie.util.translator.strategies;

import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.dto.catalog.SortingDirection;
import com.a_smart_cookie.util.translator.Translator;

/**
 * Holder of Translators for SortingDirection enum.
 * Uses Strategy pattern and lambdas for easy scaling and extending.
 *
 */
public final class SortingDirectionTranslatorStrategies {

	/**
	 * Gets Translator for SortingDirection in English.
	 *
	 * @return Translator for SortingDirection in English.
	 */
	private static Translator<SortingDirection> getEnglishSortingDirectionTranslator() {
		return element -> {
			switch (element) {
				case DESC:
					return "decreasing";

				case ASC:
				default:
					return "increasing";
			}
		};
	}

	/**
	 * Gets Translator for SortingDirection in Ukrainian.
	 *
	 * @return Translator for SortingDirection in Ukrainian.
	 */
	private static Translator<SortingDirection> getUkrainianSortingDirectionTranslator() {
		return element -> {
			switch (element) {
				case DESC:
					return "за спаданням";

				case ASC:
				default:
					return "за зростанням";
			}
		};
	}

	/**
	 * Context method for getting needed translator for SortingDirection enum by input language.
	 *
	 * @param language Language to be translated into.
	 * @return Specific Translator in the desired language.
	 */
	public static Translator<SortingDirection> getTranslatorByLanguage(Language language) {
		switch (language) {
			case ENGLISH:
				return getEnglishSortingDirectionTranslator();

			case UKRAINIAN:
			default:
				return getUkrainianSortingDirectionTranslator();
		}
	}

	private SortingDirectionTranslatorStrategies() {
	}
}
