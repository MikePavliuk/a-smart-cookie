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
		USER_SUBSCRIPTIONS("/WEB-INF/jsp/user_subscriptions.jsp"),
		ADMIN_USERS("/WEB-INF/jsp/users.jsp"),
		ADMIN_PUBLICATIONS("/WEB-INF/jsp/publications.jsp"),
		ADMIN_PUBLICATION_EDIT("/WEB-INF/jsp/publication_edit.jsp"),
		ADMIN_PUBLICATION_CREATE("/WEB-INF/jsp/publication_create.jsp");

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
		USER_SUBSCRIPTIONS("/controller?command=subscriptions"),
		ADMIN_USERS("/controller?command=users"),
		ADMIN_PUBLICATIONS_MANAGEMENT("/controller?command=publications"),
		ADMIN_PUBLICATION_EDIT("/controller?command=publication_edit_view"),
		ADMIN_PUBLICATION_CREATE("/controller?command=publication_create_view");

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