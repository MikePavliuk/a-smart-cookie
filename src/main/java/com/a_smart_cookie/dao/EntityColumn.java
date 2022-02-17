package com.a_smart_cookie.dao;

/**
 * Entity column names holder.
 *
 */
public final class EntityColumn {

	/**
	 * Holds column names of User entity.
	 *
	 */
	public enum User {
		ID("id"),
		EMAIL("email"),
		PASSWORD("password"),
		SALT("salt");

		private final String name;

		User(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * Holds column names of UserDetail entity.
	 *
	 */
	public enum UserDetail {
		ID("id"),
		NAME("name"),
		SURNAME("surname"),
		BALANCE("balance");

		private final String name;

		UserDetail(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * Holds column names of UserStatus entity.
	 *
	 */
	public enum UserStatus {
		ID("id"),
		NAME("name");

		private final String name;

		UserStatus(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * Holds column names of Role entity.
	 *
	 */
	public enum Role {
		ID("id"),
		NAME("name");

		private final String name;

		Role(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * Holds column names of Genre entity.
	 *
	 */
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

	/**
	 * Holds column names of Subscription entity.
	 *
	 */
	public enum Subscription {
		USER_ID("user_id"),
		PUBLICATION_ID("publication_id"),
		START_DATE("start_date");

		private final String name;

		Subscription(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * Holds column names of Publication entity.
	 *
	 */
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

	/**
	 * Holds column names of PublicationInfo entity.
	 *
	 */
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
