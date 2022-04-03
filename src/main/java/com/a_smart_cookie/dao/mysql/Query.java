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
		GET_ALL_ACTIVE_BY_USER_ID(
				"SELECT subscription.publication_id, subscription.start_date, subscription.period " +
						"FROM a_smart_cookie.subscription " +
						"WHERE subscription.user_id = ? " +
						"AND TIMESTAMPDIFF(MONTH, subscription.start_date, CURRENT_TIMESTAMP()) < subscription.period;"
		),

		GET_INACTIVE_COUNT_BY_USER_ID(
				"SELECT count(subscription.user_id) as count " +
						"FROM a_smart_cookie.subscription " +
						"WHERE subscription.user_id = ? " +
						"AND TIMESTAMPDIFF(MONTH, subscription.start_date, CURRENT_TIMESTAMP()) >= subscription.period;"
		),

		GET_TOTAL_SPENT_MONEY_BY_USER_ID(
				"SELECT sum(publication.price_per_month * subscription.period) as total " +
						"FROM a_smart_cookie.subscription " +
						"JOIN a_smart_cookie.publication " +
						"ON subscription.publication_id = publication.id " +
						"WHERE subscription.user_id = ?;"
		),

		GET_ACTIVE_COUNT_BY_USER_ID(
				"SELECT count(subscription.user_id) as count " +
						"FROM a_smart_cookie.subscription " +
						"WHERE subscription.user_id = ? " +
						"AND TIMESTAMPDIFF(MONTH, subscription.start_date, CURRENT_TIMESTAMP()) < subscription.period;"
		),

		CREATE_SUBSCRIPTION(
				"INSERT INTO a_smart_cookie.subscription(user_id, publication_id, period) " +
						"VALUES (?, ?, ?); "
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
		GET_BY_ID(
				"SELECT publication.id, publication.price_per_month, genre_id " +
						"FROM a_smart_cookie.publication " +
						"WHERE publication.id = ?;"
		),

		CREATE_PUBLICATION(
				"INSERT INTO a_smart_cookie.publication(genre_id, price_per_month) " +
						"VALUES (?, ?);"
		),

		CREATE_PUBLICATION_INFO(
				"INSERT INTO a_smart_cookie.publication_info(publication_id, language_id, title, description) " +
						"VALUES (?, ?, ?, ?);"
		),

		DELETE_BY_ID(
				"DELETE FROM a_smart_cookie.publication " +
						"WHERE publication.id=?;"
		),

		UPDATE_PUBLICATION_GENRE_AND_PRICE_PER_MONTH_BY_ID(
				"UPDATE a_smart_cookie.publication " +
						"SET publication.genre_id = (SELECT genre.id from a_smart_cookie.genre WHERE genre.name = ?), " +
						"publication.price_per_month = ? " +
						"WHERE publication.id = ?;"
		),

		UPDATE_PUBLICATION_INFO_BY_ID_AND_LANGUAGE(
				"UPDATE a_smart_cookie.publication_info " +
						"SET publication_info.title = ?, " +
						"publication_info.description = ? " +
						"WHERE publication_info.publication_id = ? " +
						"AND publication_info.language_id = " +
						"(SELECT id FROM a_smart_cookie.language WHERE name = ?); "
		),

		GET_TOTAL_NUMBER_OF_PUBLICATIONS(
				"SELECT count(*) as count " +
						"FROM a_smart_cookie.publication;"
		),

		GET_PUBLICATIONS_WITH_INFO_AND_OFFSET_AND_ITEMS_PER_PAGE_BY_LANGUAGE(
				"SELECT publication.id, genre.name, publication.price_per_month, " +
						"publication_info.title, publication_info.description " +
						"FROM a_smart_cookie.publication " +
						"JOIN a_smart_cookie.publication_info " +
						"ON publication.id = publication_info.publication_id " +
						"JOIN a_smart_cookie.genre " +
						"ON publication.genre_id = genre.id " +
						"WHERE publication_info.language_id = (SELECT id FROM a_smart_cookie.language WHERE name = ?) " +
						"LIMIT ?, ?;"
		),

		GET_PUBLICATION_WITH_INFO_BY_ID_AND_LANGUAGE(
				"SELECT publication.id, genre.name, publication.price_per_month, " +
						"publication_info.title, publication_info.description " +
						"FROM a_smart_cookie.publication " +
						"JOIN a_smart_cookie.publication_info " +
						"ON publication.id = publication_info.publication_id " +
						"JOIN a_smart_cookie.genre " +
						"ON publication.genre_id = genre.id " +
						"WHERE publication_id = ? " +
						"AND publication_info.language_id = (SELECT id FROM a_smart_cookie.language WHERE name = ?);"
		),

		BUILDER_FIND_ALL_BY_LANGUAGE(
				"SELECT genre.name, " +
						"publication.id, publication.price_per_month, publication_info.title, publication_info.description " +
						"FROM a_smart_cookie.publication " +
						"JOIN a_smart_cookie.publication_info " +
						"ON publication.id = publication_info.publication_id " +
						"JOIN a_smart_cookie.genre " +
						"ON publication.genre_id = genre.id " +
						"WHERE publication_info.language_id = " +
						"(SELECT id FROM a_smart_cookie.language WHERE name = ?) "
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
		),

		GET_ID_BY_NAME(
				"SELECT genre.id " +
						"FROM a_smart_cookie.genre " +
						"WHERE genre.name = ?;"
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
	 * Represents queries holder for obtaining languages.
	 */
	public enum Language {
		GET_ID_BY_NAME(
				"SELECT language.id " +
						"FROM a_smart_cookie.language " +
						"WHERE language.name = ?;"
		);

		private final String query;

		Language(String query) {
			this.query = query;
		}

		public String getQuery() {
			return query;
		}
	}

	/**
	 * Represents queries holder for obtaining roles.
	 */
	public enum Role {
		GET_ID_BY_NAME(
				"SELECT role.id " +
						"FROM a_smart_cookie.role " +
						"WHERE role.name = ?;"
		);

		private final String query;

		Role(String query) {
			this.query = query;
		}

		public String getQuery() {
			return query;
		}
	}

	/**
	 * Represents queries holder for obtaining roles.
	 */
	public enum UserStatus {
		GET_ID_BY_NAME(
				"SELECT user_status.id " +
						"FROM a_smart_cookie.user_status " +
						"WHERE user_status.name = ?;"
		);

		private final String query;

		UserStatus(String query) {
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

		UPDATE_USER_STATUS(
				"UPDATE a_smart_cookie.user " +
						"SET user.user_status_id = (SELECT user_status.id from a_smart_cookie.user_status WHERE user_status.name = ?) " +
						"WHERE user.id = ?;"
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

		GET_SUBSCRIBERS_WITH_OFFSET_AND_ITEMS_PER_PAGE(
				"SELECT user.id, user.email, user.password, user.salt, " +
						"user_detail.id as userdetail_id, user_detail.name, user_detail.surname, user_detail.balance, " +
						"user_status.name as userstatus_name, role.name as role_name " +
						"FROM a_smart_cookie.user " +
						"JOIN a_smart_cookie.user_detail " +
						"ON user.id = user_detail.user_id " +
						"JOIN a_smart_cookie.user_status " +
						"ON user.user_status_id = user_status.id " +
						"JOIN a_smart_cookie.role " +
						"ON user.role_id = role.id " +
						"WHERE user.role_id = (SELECT role.id from a_smart_cookie.role where role.name = 'subscriber') " +
						"LIMIT ?, ?;"
		),

		GET_ALL_SUBSCRIBERS(
				"SELECT user.id, user.email, user.password, user.salt, " +
						"user_detail.id as userdetail_id, user_detail.name, user_detail.surname, user_detail.balance, " +
						"user_status.name as userstatus_name, role.name as role_name " +
						"FROM a_smart_cookie.user " +
						"JOIN a_smart_cookie.user_detail " +
						"ON user.id = user_detail.user_id " +
						"JOIN a_smart_cookie.user_status " +
						"ON user.user_status_id = user_status.id " +
						"JOIN a_smart_cookie.role " +
						"ON user.role_id = role.id " +
						"WHERE user.role_id = (SELECT role.id from a_smart_cookie.role where role.name = 'subscriber');"
		),

		GET_NUMBER_OF_SUBSCRIBERS(
				"SELECT count(*) as count " +
						"FROM a_smart_cookie.user " +
						"WHERE user.role_id = (SELECT role.id from a_smart_cookie.role WHERE role.name = 'subscriber');"
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
		ADD_BALANCE_TO_USER_BY_ID(
				"UPDATE a_smart_cookie.user_detail " +
						"SET user_detail.balance = user_detail.balance + ? " +
						"WHERE user_detail.user_id = ?;"
		),

		MINUS_BALANCE_FROM_USER_BY_ID(
				"UPDATE a_smart_cookie.user_detail " +
						"SET user_detail.balance = user_detail.balance - ? " +
						"WHERE user_detail.user_id = ?;"
		),

		GET_BALANCE_BY_USER_ID(
				"SELECT user_detail.balance " +
						"FROM a_smart_cookie.user_detail " +
						"WHERE user_detail.user_id = ?;"
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
