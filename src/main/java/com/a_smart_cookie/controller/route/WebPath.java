package com.a_smart_cookie.controller.route;

/**
 * Path holder (jsp pages, controller commands).
 *
 */
public final class WebPath {

	/**
	 * Represents jsp pages addresses.
	 *
	 */
	public enum Page implements Routable {
		ERROR("/WEB-INF/jsp/error.jsp"),
		CATALOG("/WEB-INF/jsp/catalog.jsp"),
		SIGN_IN("/sign_in.jsp"),
		SIGN_UP("/sign_up.jsp");

		private final String value;

		Page(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * Represents controller commands.
	 *
	 */
	public enum Command implements Routable {
		CATALOG_FIRST_PAGE("/controller?command=catalog&page=1");

		private final String value;

		Command(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	private WebPath() {
	}

}