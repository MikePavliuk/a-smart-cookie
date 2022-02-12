package com.a_smart_cookie.util.translator.strategies;

import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.util.sorting.SortingDirection;
import com.a_smart_cookie.util.translator.Translator;

public final class SortingDirectionTranslatorStrategies {

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
