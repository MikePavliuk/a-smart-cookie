package com.a_smart_cookie.util.translator;

import com.a_smart_cookie.entity.Language;

/**
 * Functional interface that provides with ability to get translated value by language.
 *
 */
public interface Translatable {
	String getTranslatedValue(Language language);
}
