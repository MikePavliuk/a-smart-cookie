package com.a_smart_cookie.util.translator;

public interface Translator<T extends Translatable> {
	String translate(T element);
}
