package com.a_smart_cookie.dao.mysql;

public final class Query {

    private Query() {}

    public enum Genre {
        FIND_ALL_BY_LANGUAGE(
                "SELECT genre.id, genre_translate.name " +
                        "FROM a_smart_cookie.genre " +
                        "JOIN a_smart_cookie.genre_translate " +
                        "ON genre.id = genre_translate.genre_id " +
                        "WHERE genre_translate.language_id = " +
                            "(SELECT id " +
                            "FROM a_smart_cookie.language " +
                            "WHERE language.name = ?)");

        private final String query;

        Genre(String query) {
            this.query = query;
        }

        public String getQuery() {
            return query;
        }
    }
}
