package com.a_smart_cookie.util.translator.strategies;

import com.a_smart_cookie.entity.Genre;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.util.translator.Translator;

/**
 * Holder of Translators for Genre enum.
 * Uses Strategy pattern and lambdas for easy scaling and extending.
 *
 */
public final class GenreTranslatorStrategies {

	/**
	 * Gets Translator for Genre in English
	 *
	 * @return Translator for Genre in English
	 */
	private static Translator<Genre> getEnglishGenreTranslator() {
		return element -> {
			switch (element) {
				case NOVEL:
					return "novel";

				case COOKBOOK:
					return "cookbook";

				case DETECTIVE:
					return "detective";

				case HISTORICAL:
					return "historical";

				case HORROR:
					return "horror";

				case SCIENCE:
					return "science";

				case FICTION:
				default:
					return "fiction";

			}
		};
	}

	/**
	 * Gets Translator for Genre in Ukrainian
	 *
	 * @return Translator for Genre in Ukrainian
	 */
	private static Translator<Genre> getUkrainianGenreTranslator() {
		return element -> {
			switch (element) {
				case NOVEL:
					return "новела";

				case COOKBOOK:
					return "кулінарія";

				case DETECTIVE:
					return "детектив";

				case HISTORICAL:
					return "історія";

				case HORROR:
					return "жахи";

				case SCIENCE:
					return "наука";

				case FICTION:
				default:
					return "фантастика";

			}
		};
	}

	/**
	 * Context method for getting needed translator for Genre enum by input language.
	 *
	 * @param language Language to be translated into.
	 * @return Specific Translator in the desired language.
	 */
	public static Translator<Genre> getTranslatorByLanguage(Language language) {
		switch (language) {
			case ENGLISH:
				return getEnglishGenreTranslator();

			case UKRAINIAN:
			default:
				return getUkrainianGenreTranslator();
		}
	}


	private GenreTranslatorStrategies() {
	}

}
