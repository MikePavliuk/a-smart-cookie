package com.a_smart_cookie.dao.mysql;

public final class Query {

    private Query() {}

	public enum Publication {
		FIND_ALL_BY_LANGUAGE(
				"SELECT genre.id, genre.name, " +
							"publication.id, publication.price_per_month, publication_info.title, publication_info.description " +
						"FROM a_smart_cookie.publication " +
						"JOIN a_smart_cookie.publication_info " +
						"ON publication.id = publication_info.publication_id " +
						"JOIN a_smart_cookie.genre " +
						"ON publication.genre_id = genre.id " +
						"WHERE publication_info.language_id = " +
							"(SELECT id FROM a_smart_cookie.language WHERE name = ?);"
		),

		FIND_LIMIT_WITH_OFFSET_BY_LANGUAGE_NATURAL_ORDERING(
				"SELECT genre.id, genre.name, " +
						"publication.id, publication.price_per_month, publication_info.title, publication_info.description " +
						"FROM a_smart_cookie.publication " +
						"JOIN a_smart_cookie.publication_info " +
						"ON publication.id = publication_info.publication_id " +
						"JOIN a_smart_cookie.genre " +
						"ON publication.genre_id = genre.id " +
						"WHERE publication_info.language_id = " +
							"(SELECT id FROM a_smart_cookie.language WHERE name = ?) " +
						"LIMIT ?, ?;"
		),

		GET_TOTAL_NUMBER_OF_ROWS(
				"SELECT count(*) AS count FROM a_smart_cookie.publication;"
		);

		private final String query;

		Publication(String query) {
			this.query = query;
		}

		public String getQuery() {
			return query;
		}
	}

}
