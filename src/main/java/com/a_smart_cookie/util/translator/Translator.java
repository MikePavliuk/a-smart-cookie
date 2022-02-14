package com.a_smart_cookie.util.translator;

/**
 * Functional interface that provides with ability to translate values of classes, which will implement it.
 *
 */
public interface Translator<T extends Translatable> {
	String translate(T element);
}
