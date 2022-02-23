package com.a_smart_cookie.dao.mysql;

import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.dao.GenreDao;
import com.a_smart_cookie.dao.ResourceReleaser;
import com.a_smart_cookie.entity.Genre;
import com.a_smart_cookie.exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for genre related entities implemented with MySql
 *
 */
public class MysqlGenreDao extends GenreDao {

	private static final Logger LOG = Logger.getLogger(MysqlGenreDao.class);

	@Override
	public List<Genre> findAllUsedInPublicationsGenres() throws DaoException {
		LOG.debug("MysqlGenreDao starts finding all used in publications genres");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(Query.Genre.GET_ALL_DISTINCT_USED_IN_PUBLICATION_TABLE.getQuery());
			rs = pstmt.executeQuery();

			LOG.debug("MysqlGenreDao finished finding all used in publications genres");
			return extractGenres(rs);

		} catch (SQLException e) {
			LOG.error("Can't find used in publications genres", e);
			throw new DaoException("Can't find used in publications genres", e);
		} finally {
			ResourceReleaser.close(rs);
			ResourceReleaser.close(pstmt);
		}
	}

	/**
	 * Extracts genres from ResultSet to list of genres.
	 *
	 * @param rs External ResultSet
	 * @return List of genres
	 */
	private List<Genre> extractGenres(ResultSet rs) throws SQLException {
		List<Genre> genres = new ArrayList<>();

		while (rs.next()) {
			genres.add(extractGenre(rs));
		}
		return genres;
	}

	/**
	 * Extracts genre from ResultSet to Genre enum value
	 *
	 * @param rs External ResultSet
	 * @return Genre enum value
	 */
	private Genre extractGenre(ResultSet rs) throws SQLException {
		return Genre.fromString(rs.getString(EntityColumn.Genre.NAME.getName()));
	}

}
