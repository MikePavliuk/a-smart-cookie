package com.a_smart_cookie.util;

/**
 * Util class for handling operations on Strings.
 *
 */
public final class StringHandler {

	/**
	 * Capitalises first letter of given string.
	 *
	 * @param input String meant to be capitalised on first letter.
	 * @return Capitalized fist letter string.
	 */
	public static String capitaliseFirstLetter(String input){
		if (input == null || input.isEmpty()) {
			throw new IllegalArgumentException("Input can't be null or empty");
		}
		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}

	private StringHandler() {
	}
}
