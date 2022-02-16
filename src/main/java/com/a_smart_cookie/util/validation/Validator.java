package com.a_smart_cookie.util.validation;

/**
 * Functional interface for validating elements.
 *
 * @param <E> Type of input value.
 * @param <T> Return type of validation.
 */
public interface Validator<E, T> {
	T isValid(E element);
}
