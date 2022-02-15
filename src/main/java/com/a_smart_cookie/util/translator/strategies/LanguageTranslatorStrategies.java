package com.a_smart_cookie.util.translator.strategies;

import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.util.translator.Translator;

/**
 * Holder of Translators for Language enum.
 * Uses Strategy pattern and lambdas for easy scaling and extending.
 *
 */
public final class LanguageTranslatorStrategies {

	/**
	 * Gets Translator for Language in English
	 *
	 * @return Translator for Language in English
	 */
	private static Translator<Language> getEnglishLanguageTranslator() {
		return element -> {
			switch (element) {
				case ENGLISH:
					return "english";

				case UKRAINIAN:
				default:
					return "ukrainian";
			}
		};
	}

	/**
	 * Gets Translator for Genre in Ukrainian
	 *
	 * @return Translator for Genre in Ukrainian
	 */
	private static Translator<Language> getUkrainianLanguageTranslator() {
		return element -> {
			switch (element) {
				case ENGLISH:
					return "англійська";

				case UKRAINIAN:
				default:
					return "українська";
			}
		};
	}

	/**
	 * Context method for getting needed translator for Language enum by input language.
	 *
	 * @param language Language to be translated into.
	 * @return Specific Translator in the desired language.
	 */
	public static Translator<Language> getTranslatorByLanguage(Language language) {
		switch (language) {
			case ENGLISH:
				return getEnglishLanguageTranslator();

			case UKRAINIAN:
			default:
				return getUkrainianLanguageTranslator();
		}
	}

	private LanguageTranslatorStrategies() {
	}
}
