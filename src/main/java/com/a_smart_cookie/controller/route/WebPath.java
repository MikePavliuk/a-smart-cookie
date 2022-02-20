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
		CATALOG("/WEB-INF/jsp/catalog.jsp"),
		SIGN_IN("/WEB-INF/jsp/sign_in.jsp"),
		SIGN_UP("/WEB-INF/jsp/sign_up.jsp"),
		USER_SUBSCRIPTIONS("/WEB-INF/jsp/user_subscriptions.jsp");

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
		CATALOG_FIRST_PAGE("/controller?command=catalog&page=1"),
		SIGN_IN("/controller?command=sign-in"),
		SIGN_UP("/controller?command=sign-up"),
		ERROR("/controller?command=error"),
		USER_SUBSCRIPTIONS("/controller?command=subscriptions");

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