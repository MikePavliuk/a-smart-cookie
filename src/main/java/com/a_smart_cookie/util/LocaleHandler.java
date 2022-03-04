package com.a_smart_cookie.util;

import com.a_smart_cookie.entity.Language;

import java.util.Locale;

/**
 * Provides with locale handling.
 *
 */
public final class LocaleHandler {

	/**
	 * Gets locale by language.
	 *
	 * @param language Language to choose locale from.
	 * @return Locale.
	 */
	public static Locale getLocaleByLanguage(Language language) {

		switch (language) {
			case ENGLISH:
				return Locale.UK;

			case UKRAINIAN:
			default:
				return Locale.forLanguageTag("uk-UA");
		}
	}

	private LocaleHandler() {
	}

}
