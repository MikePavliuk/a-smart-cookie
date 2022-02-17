package com.a_smart_cookie.dao.mysql;

import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.dao.PublicationDao;
import com.a_smart_cookie.dao.ResourceReleaser;
import com.a_smart_cookie.dto.catalog.CountRowsParameters;
import com.a_smart_cookie.dto.catalog.FilterParameters;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for publication related entities implemented with MySql
 *
 */
public class MysqlPublicationDao extends PublicationDao {

	private static final Logger LOG = Logger.getLogger(MysqlPublicationDao.class);

	@Override
	public List<Publication> findPublicationsByFilterParameters(FilterParameters filterParameters) throws DaoException {
		LOG.debug("MysqlPublicationDao starts finding publications by filter parameters");

		StringBuilder queryBuilder = getQueryWithAppliedFilterParameters(filterParameters);
		LOG.trace("Created query --> " + queryBuilder);

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(queryBuilder.toString());
			pstmt.setString(1, filterParameters.getLanguage().name().toLowerCase());
			rs = pstmt.executeQuery();

			LOG.debug("MysqlPublicationDao finished finding publications by filter parameters");
			return extractPublications(rs);

		} catch (SQLException e) {
			LOG.error("Can't find all publications with query '" + queryBuilder + "'", e);
			throw new DaoException("Can't find all publications with query '" + queryBuilder + "'", e);
		} finally {
			ResourceReleaser.close(rs);
			ResourceReleaser.close(pstmt);
		}
	}

	/**
	 * 	Construct query with filter parameters for getting requested publications.
	 *
	 * @param filterParameters Possible parameters for constructing query
	 * @return Built query in StringBuilder
	 */
	private StringBuilder getQueryWithAppliedFilterParameters(FilterParameters filterParameters) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(Query.Publication.BUILDER_FIND_ALL_BY_LANGUAGE.getQuery());

		if (filterParameters.getSpecificGenre() != null) {
			queryBuilder.append(" AND genre.name = '")
					.append(filterParameters.getSpecificGenre().name().toLowerCase())
					.append("'");
		}

		if (filterParameters.getSearchedTitle() != null) {
			queryBuilder.append(" AND UPPER(publication_info.title) LIKE UPPER('%")
					.append(filterParameters.getSearchedTitle())
					.append("%') ");
		}

		queryBuilder.append(" ORDER BY ");
		queryBuilder.append(filterParameters.getSortingParameter().getValue());
		queryBuilder.append(" ");
		queryBuilder.append(filterParameters.getSortingDirection());

		queryBuilder.append(" LIMIT ")
				.append(filterParameters.getPaginationOffset())
				.append(", ")
				.append(filterParameters.getItemsPerPage());

		queryBuilder.append(";");

		return queryBuilder;
	}

	/**
	 * Extracts publications from ResultSet to List of publications.
	 *
	 * @param rs External ResultSet
	 * @return List of extracted publications
	 */
	private List<Publication> extractPublications(ResultSet rs) throws SQLException {
		List<Publication> publications = new ArrayList<>();

		while (rs.next()) {
			publications.add(extractPublication(rs));
		}

		return publications;
	}

	/**
	 * Method to extract publication from ResultSet.
	 *
	 * @param rs External ResultSet
	 * @return Extracted publication
	 */
	private Publication extractPublication(ResultSet rs) throws SQLException {
		return new Publication(
				rs.getInt(EntityColumn.Publication.ID.getName()),
				Publication.Genre.safeFromString(rs.getString(EntityColumn.Genre.NAME.getName())),
				rs.getString(EntityColumn.PublicationInfo.TITLE.getName()),
				rs.getString(EntityColumn.PublicationInfo.DESCRIPTION.getName()),
				rs.getBigDecimal(EntityColumn.Publication.PRICE_PER_MONTH.getName())
		);
	}

	@Override
	public int getTotalNumberOfRequestedQueryRows(CountRowsParameters countRowsParameters) throws DaoException {
		LOG.debug("MysqlPublicationDao starts getting total number of rows by parameters");

		StringBuilder queryBuilder = getQueryWithAppliedCountRowsParameters(countRowsParameters);
		LOG.trace("Created query --> " + queryBuilder);

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(queryBuilder.toString());
			pstmt.setString(1, countRowsParameters.getLanguage().name().toLowerCase());
			rs = pstmt.executeQuery();

			int numberOfRows = 0;

			if (rs.next()) {
				numberOfRows = rs.getInt("count");
			}

			LOG.debug("MysqlPublicationDao finished getting total number of rows by parameters");
			return numberOfRows;

		} catch (SQLException e) {
			LOG.error("Can't count publications with query '" + queryBuilder + "'", e);
			throw new DaoException("Can't count publications with query '" + queryBuilder + "'", e);
		} finally {
			ResourceReleaser.close(rs);
			ResourceReleaser.close(pstmt);
		}
	}

	/**
	 * Construct query with CountRowsParameters for getting count of requested rows.
	 *
	 * @param countRowsParameters Possible parameters for constructing query
	 * @return Built query in StringBuilder
	 */
	private StringBuilder getQueryWithAppliedCountRowsParameters(CountRowsParameters countRowsParameters) {
		StringBuilder queryBuilder = new StringBuilder();

		queryBuilder.append(Query.Publication.BUILDER_GET_NUMBER_OF_ROWS_FOUNDED_BY_LANGUAGE.getQuery());

		if (countRowsParameters.getGenre() != null ) {
			queryBuilder.append(" AND genre.name = '")
					.append(countRowsParameters.getGenre().name().toLowerCase())
					.append("'");
		}

		if (countRowsParameters.getSearchedTitle() != null) {
			queryBuilder.append(" AND UPPER(publication_info.title) LIKE UPPER('%")
					.append(countRowsParameters.getSearchedTitle())
					.append("%') ");
		}

		queryBuilder.append(";");
		return queryBuilder;
	}
}
