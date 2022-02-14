package com.a_smart_cookie.util.translator;

/**
 * Provides with ability to translate generic Translatable object with generic Translator object.
 *
 */
public final class TranslatorContext {

	public static <T extends Translatable> String translateInto(Translator<T> translator, T translatable) {
		return translator.translate(translatable);
	}

	private TranslatorContext() {
	}

}
