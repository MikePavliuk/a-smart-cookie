package com.a_smart_cookie.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;


/**
 * Util class for handling operations on Cookies.
 *
 */
public final class CookieHandler {

	/**
	 * Reads cookie string value from request by key.
	 *
	 * @param req External HttpServletRequest param.
	 * @param key Name of searched cookie.
	 * @return Optional value of requested cookie.
	 */
	public static Optional<String> readCookieValue(HttpServletRequest req, String key) {
		Optional<Cookie> cookie = readCookie(req, key);
		return cookie.map(Cookie::getValue);
	}

	/**
	 * Reads Cookie from request by key.
	 *
	 * @param req External HttpServletRequest param.
	 * @param key Name of searched cookie.
	 * @return Optional cookie by requested key.
	 */
	public static Optional<Cookie> readCookie(HttpServletRequest req, String key) {
		if (req == null || key == null) {
			throw new IllegalArgumentException("Input parameters can't be null");
		}

		return Arrays.stream(req.getCookies())
				.filter(c -> key.equals(c.getName()))
				.findAny();
	}

	/**
	 * Creates new cookie.
	 *
	 * @param key Name of new cookie.
	 * @param value Value of new cookie.
	 * @param maxAge Time of saving cookie in milliseconds.
	 * @param path Path for attaching cookie.
	 * @return Ready to use cookie object.
	 */
	public static Cookie createCookie(String key, String value, int maxAge, String path) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath(path);
		return cookie;
	}

	/**
	 * Updates cookie in response.
	 *
	 * @param key Name of searched cookie.
	 * @param newValue Updated value of cookie.
	 * @param maxAge Time of saving cookie in milliseconds.
	 * @param path Path for attaching cookie.
	 * @param req External HttpServletRequest param.
	 * @param resp External HttpServletResponse param.
	 * @return Boolean value whether cookie was updated or no.
	 */
	public static boolean updateCookie(String key, String newValue, int maxAge, String path, HttpServletRequest req, HttpServletResponse resp) {
		Optional<Cookie> optionalCookie = readCookie(req, key);

		if (optionalCookie.isPresent()) {
			Cookie cookie = optionalCookie.get();
			cookie.setValue(newValue);
			cookie.setMaxAge(maxAge);
			cookie.setPath(path);
			resp.addCookie(cookie);

			return true;
		}

		return false;
	}


	private CookieHandler() {
	}

}
