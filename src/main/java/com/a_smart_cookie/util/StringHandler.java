package com.a_smart_cookie.util;

public final class StringHandler {

	public static String capitaliseFirstLetter(String input){
		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}

	private StringHandler() {
	}
}
