package com.a_smart_cookie.util.translator.strategies;

import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.util.sorting.SortingParameter;
import com.a_smart_cookie.util.translator.Translator;

public final class SortingParameterTranslatorStrategies {

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
