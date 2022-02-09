package com.a_smart_cookie.dao;

public final class EntityColumn {

	public enum Genre {
		ID("id"),
		NAME("name");

		private final String name;

		Genre(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public enum Publication {
		ID("id"),
		PRICE_PER_MONTH("price_per_month");

		private final String name;

		Publication(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public enum PublicationInfo {
		TITLE("title"),
		DESCRIPTION("description");

		private final String name;

		PublicationInfo(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	private EntityColumn() {}

}
