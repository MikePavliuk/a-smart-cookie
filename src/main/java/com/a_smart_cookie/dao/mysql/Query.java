package com.a_smart_cookie.dao.mysql;

/**
 * Queries holder for db entities.
 */
public final class Query {

	private Query() {
	}

	/**
	 * Represents queries holder for obtaining publications.
	 */
	public enum Subscription {
		GET_ALL_BY_USER_ID(
				"SELECT subscription.publication_id, subscription.start_date " +
						"FROM a_smart_cookie.subscription " +
						"WHERE subscription.user_id = ?;"
		);

		private final String query;

		Subscription(String query) {
			this.query = query;
		}

		public String getQuery() {
			return query;
		}
	}


	/**
	 * Represents queries holder for obtaining publications.
	 */
	public enum Publication {
		BUILDER_FIND_ALL_BY_LANGUAGE(
				"SELECT genre.name, " +
						"publication.id, publication.price_per_month, publication_info.title, publication_info.description " +
						"FROM a_smart_cookie.publication " +
						"JOIN a_smart_cookie.publication_info " +
						"ON publication.id = publication_info.publication_id " +
						"JOIN a_smart_cookie.genre " +
						"ON publication.genre_id = genre.id " +
						"WHERE publication_info.language_id = " +
						"(SELECT id FROM a_smart_cookie.language WHERE name = ?)"
		),

		BUILDER_GET_NUMBER_OF_ROWS_FOUNDED_BY_LANGUAGE(
				"SELECT count(*) AS count FROM a_smart_cookie.publication " +
						"JOIN a_smart_cookie.publication_info " +
						"ON publication.id = publication_info.publication_id " +
						"JOIN a_smart_cookie.genre " +
						"ON publication.genre_id = genre.id " +
						"WHERE publication_info.language_id = " +
						"(SELECT id FROM a_smart_cookie.language WHERE name = ?) "
		);

		private final String query;

		Publication(String query) {
			this.query = query;
		}

		public String getQuery() {
			return query;
		}
	}

	/**
	 * Represents queries holder for obtaining genres.
	 */
	public enum Genre {
		GET_ALL_DISTINCT_USED_IN_PUBLICATION_TABLE(
				"SELECT DISTINCT genre.name " +
						"FROM a_smart_cookie.genre " +
						"JOIN a_smart_cookie.publication " +
						"ON genre.id = publication.genre_id;"
		);

		private final String query;

		Genre(String query) {
			this.query = query;
		}

		public String getQuery() {
			return query;
		}
	}

	/**
	 * Represents queries holder for user entity.
	 */
	public enum User {
		CHECK_IF_USER_EXISTS_BY_EMAIL(
				"SELECT EXISTS(SELECT * from a_smart_cookie.user WHERE email=?);"
		),

		GET_USER_BY_EMAIL(
				"SELECT user.id, user.email, user.password, user.salt, " +
						"user_detail.id as userdetail_id, user_detail.name, user_detail.surname, user_detail.balance, " +
						"user_status.name as userstatus_name, role.name as role_name " +
						"FROM a_smart_cookie.user " +
						"LEFT JOIN a_smart_cookie.user_detail " +
						"ON user.id = user_detail.user_id " +
						"JOIN a_smart_cookie.user_status " +
						"ON user.user_status_id = user_status.id " +
						"JOIN a_smart_cookie.role " +
						"ON user.role_id = role.id " +
						"WHERE user.email=?;"
		),

		INSERT_USER(
				"INSERT INTO a_smart_cookie.user(email, password, salt, role_id, user_status_id) " +
						"VALUES (?, ?, ?, ?, ?);"
		);

		private final String query;

		User(String query) {
			this.query = query;
		}

		public String getQuery() {
			return query;
		}
	}

	/**
	 * Represents queries holder for user detail entity.
	 */
	public enum UserDetail {
		GET_USER_DETAIL_BY_USER_ID(
				"SELECT id, name, surname, balance " +
						"FROM a_smart_cookie.user_detail " +
						"WHERE user_id = ?;"
		),

		INSERT_USER_DETAIL(
				"INSERT INTO a_smart_cookie.user_detail(name, surname, user_id) " +
						"VALUES (?, ?, ?);"
		);

		private final String query;

		UserDetail(String query) {
			this.query = query;
		}

		public String getQuery() {
			return query;
		}
	}

}
