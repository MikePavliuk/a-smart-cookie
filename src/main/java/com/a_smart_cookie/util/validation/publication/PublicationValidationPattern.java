package com.a_smart_cookie.util.validation.publication;

/**
 * Holds validation patterns for publication.
 *
 */
public enum PublicationValidationPattern {

	TITLE(".{2,100}"),
	DESCRIPTION(".{10,255}"),
	MIN_PRICE_PER_MONTH("0.01");

	private final String pattern;

	PublicationValidationPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}

}
