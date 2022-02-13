package com.a_smart_cookie.dao.mysql;

import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.dao.GenreDao;
import com.a_smart_cookie.dao.ResourceReleaser;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlGenreDao extends GenreDao {

	@Override
	public List<Publication.Genre> findAllUsedInPublicationsGenres() throws DaoException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(Query.Genre.GET_ALL_DISTINCT_USED_IN_PUBLICATION_TABLE.getQuery());
			rs = pstmt.executeQuery();

			return extractGenres(rs);

		} catch (SQLException e) {
			throw new DaoException("Can't find used in publications genres", e);
		} finally {
			ResourceReleaser.close(rs);
			ResourceReleaser.close(pstmt);
		}
	}

	private List<Publication.Genre> extractGenres(ResultSet rs) throws SQLException {
		List<Publication.Genre> genres = new ArrayList<>();

		while (rs.next()) {
			genres.add(extractGenre(rs));
		}
		return genres;
	}

	private Publication.Genre extractGenre(ResultSet rs) throws SQLException {
		return Publication.Genre.fromString(rs.getString(EntityColumn.Genre.NAME.getName()));
	}

}
