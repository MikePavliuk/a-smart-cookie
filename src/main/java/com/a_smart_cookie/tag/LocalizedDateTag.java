package com.a_smart_cookie.tag;

import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.util.LocaleHandler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Custom jstl tag for converting local date into localized display one
 */
public class LocalizedDateTag {

	/**
	 * Converts LocalDate object into string representation depends on language.
	 *
	 * @param localDate Local date to be displayed.
	 * @param language  Language for choosing proper locale.
	 * @return String localized date.
	 * @throws IllegalArgumentException Thrown if at least one input parameter is null.
	 */
	public static String convert(LocalDate localDate, Language language) {

		if (localDate == null || language  == null) {
			throw new IllegalArgumentException("Parameters can't be null!");
		}

		return localDate.format(
				DateTimeFormatter
						.ofLocalizedDate(FormatStyle.SHORT)
						.withLocale(LocaleHandler.getLocaleByLanguage(language))
		);

	}

}
