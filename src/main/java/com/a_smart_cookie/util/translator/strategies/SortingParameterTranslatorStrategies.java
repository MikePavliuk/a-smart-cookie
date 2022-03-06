package com.a_smart_cookie.util.translator.strategies;

import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.dto.catalog.SortingParameter;
import com.a_smart_cookie.util.translator.Translator;

/**
 * Holder of Translators for SortingParameter enum.
 * Uses Strategy pattern and lambdas for easy scaling and extending.
 *
 */
public final class SortingParameterTranslatorStrategies {

	/**
	 * Gets Translator for SortingParameter in English.
	 *
	 * @return Translator for SortingParameter in English.
	 */
	private static Translator<SortingParameter> getEnglishSortingParameterTranslator() {
		return element -> {
			switch (element) {
				case PRICE:
					return "price";

				case TITLE:
				default:
					return "title";
			}
		};
	}

	/**
	 * Gets Translator for SortingParameter in Ukrainian.
	 *
	 * @return Translator for SortingParameter in Ukrainian.
	 */
	private static Translator<SortingParameter> getUkrainianSortingParameterTranslator() {
		return element -> {
			switch (element) {
				case PRICE:
					return "ціна";

				case TITLE:
				default:
					return "назва";
			}
		};
	}

	/**
	 * Context method for getting needed translator for SortingParameter enum by input language.
	 *
	 * @param language Language to be translated into.
	 * @return Specific Translator in the desired language.
	 */
	public static Translator<SortingParameter> getTranslatorByLanguage(Language language) {
		switch (language) {
			case ENGLISH:
				return getEnglishSortingParameterTranslator();

			case UKRAINIAN:
			default:
				return getUkrainianSortingParameterTranslator();
		}
	}


	private SortingParameterTranslatorStrategies() {
	}
}
