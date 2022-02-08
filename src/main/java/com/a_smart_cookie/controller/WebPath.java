package com.a_smart_cookie.controller;

public final class WebPath {

	public enum Page {
		ERROR("/WEB-INF/jsp/error.jsp"),
		CATALOG("/WEB-INF/jsp/catalog.jsp");

		private final String value;

		Page(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public enum Command {
		GENRES_IN_ENGLISH("/controller?command=catalog&lang=ua");

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