package com.a_smart_cookie.dao;

import com.a_smart_cookie.entity.Genre;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.exception.DaoException;

import java.util.List;

public abstract class GenreDao extends AbstractDao<Genre> {

    public enum ColumnName {
        ID("id"),
        NAME("name");

        private final String name;

        ColumnName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public abstract List<Genre> findAllByLanguage(Language language) throws DaoException;
}
