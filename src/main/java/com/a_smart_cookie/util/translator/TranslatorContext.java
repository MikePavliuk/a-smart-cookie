package com.a_smart_cookie.util.translator;

public final class TranslatorContext {

	public static <T extends Translatable> String translateInto(Translator<T> translator, T translatable) {
		return translator.translate(translatable);
	}

}
