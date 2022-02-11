package com.a_smart_cookie.web.translator;

public interface Translator<T extends Translatable> {
	String translate(T element);
}
