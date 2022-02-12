package com.a_smart_cookie.util.translator.strategies;

import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.util.translator.Translator;

public final class GenreTranslatorStrategies {

	private static Translator<Publication.Genre> getEnglishGenreTranslator() {
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

	private static Translator<Publication.Genre> getUkrainianGenreTranslator() {
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

	public static Translator<Publication.Genre> getTranslatorByLanguage(Language language) {
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
