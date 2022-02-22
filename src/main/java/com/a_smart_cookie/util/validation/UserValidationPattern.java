package com.a_smart_cookie.util.validation;

/**
 * Holds validation patterns.
 *
 */
public enum UserValidationPattern {

	NAME("[A-Z][a-z]{1,49}"),
	SURNAME("[A-Z][a-z]{1,49}"),
	EMAIL("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"),
	PASSWORD("(?=^.{8,}$)(?=.*\\d)(?=.*[!@#$%^&*]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).{8,32}$");

	private final String pattern;

	UserValidationPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}

}
