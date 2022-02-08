package com.a_smart_cookie.dao.mysql;

import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.dao.GenreDao;
import com.a_smart_cookie.dao.ResourceReleaser;
import com.a_smart_cookie.entity.Genre;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlGenreDao extends GenreDao {

    @Override
    public List<Genre> findAllByLanguage(Language language) throws DaoException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(Query.Genre.FIND_ALL_BY_LANGUAGE.getQuery());
            pstmt.setString(1, language.name().toLowerCase());
            rs = pstmt.executeQuery();
            List<Genre> genres = new ArrayList<>();

            while (rs.next()) {
                genres.add(new Genre(
                        rs.getInt(EntityColumn.Genre.ID.getName()),
                        rs.getString(EntityColumn.Genre.NAME.getName())));
            }

            return genres;

        } catch (SQLException e) {
            throw new DaoException("Exception occurred while finding all genres in " + language, e);
        } finally {
            ResourceReleaser.close(rs);
            ResourceReleaser.close(pstmt);
        }
    }

}
