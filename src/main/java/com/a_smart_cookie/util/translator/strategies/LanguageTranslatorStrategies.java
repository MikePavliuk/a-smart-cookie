package com.a_smart_cookie.util.translator.strategies;

import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.util.translator.Translator;

public final class LanguageTranslatorStrategies {

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
