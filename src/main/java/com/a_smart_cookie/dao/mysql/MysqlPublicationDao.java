package com.a_smart_cookie.dao.mysql;

import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.dao.PublicationDao;
import com.a_smart_cookie.dao.ResourceReleaser;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlPublicationDao extends PublicationDao {

	@Override
	public List<Publication> findAllByLanguage(Language language) throws DaoException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(Query.Publication.FIND_ALL_BY_LANGUAGE.getQuery());
			pstmt.setString(1, language.name().toLowerCase());
			rs = pstmt.executeQuery();

			List<Publication> publications = new ArrayList<>();

			while (rs.next()) {
				publications.add(extractPublication(rs));
			}

			return publications;

		} catch (SQLException e) {
			throw new DaoException("Exception occurred while finding all publications in " + language, e);
		} finally {
			ResourceReleaser.close(rs);
			ResourceReleaser.close(pstmt);
		}
	}

	@Override
	public List<Publication> findLimitedWithOffsetByLanguage(int limit, int offset, Language language) throws DaoException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(Query.Publication.FIND_LIMIT_WITH_OFFSET_BY_LANGUAGE_NATURAL_ORDERING.getQuery());
			pstmt.setString(1, language.name().toLowerCase());
			pstmt.setInt(2, offset);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();

			List<Publication> publications = new ArrayList<>();

			while (rs.next()) {
				publications.add(extractPublication(rs));
			}

			return publications;

		} catch (SQLException e) {
			throw new DaoException("Exception occurred while finding " + limit +" publications with offset of " + offset + " in " + language, e);
		} finally {
			ResourceReleaser.close(rs);
			ResourceReleaser.close(pstmt);
		}
	}

	private Publication extractPublication(ResultSet rs) throws SQLException {
		return new Publication(
				Publication.Genre.fromString(rs.getString(EntityColumn.Genre.NAME.getName())),
				rs.getString(EntityColumn.PublicationInfo.TITLE.getName()),
				rs.getString(EntityColumn.PublicationInfo.DESCRIPTION.getName()),
				rs.getBigDecimal(EntityColumn.Publication.PRICE_PER_MONTH.getName())
		);
	}

	@Override
	public int getTotalNumberOfPublications() throws DaoException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(Query.Publication.GET_TOTAL_NUMBER_OF_ROWS.getQuery());
			rs = pstmt.executeQuery();

			int totalNumberOfRows = 0;

			if (rs.next()) {
				totalNumberOfRows = rs.getInt("count");
			}

			return totalNumberOfRows;

		} catch (SQLException e) {
			throw new DaoException("Exception occurred while getting total number of publications", e);
		} finally {
			ResourceReleaser.close(rs);
			ResourceReleaser.close(pstmt);
		}
	}
}
