package com.a_smart_cookie.dao.mysql;

/**
 * Queries holder for db entities.
 *
 */
public final class Query {

	private Query() {
	}

	/**
	 * Represents queries holder for obtaining publications.
	 *
	 */
	public enum Publication {
		BUILDER_FIND_ALL_BY_LANGUAGE(
				"SELECT genre.id, genre.name, " +
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
	 *
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

}
