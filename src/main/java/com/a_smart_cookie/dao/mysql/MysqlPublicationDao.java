package com.a_smart_cookie.dao.mysql;

import com.a_smart_cookie.adapter.filtering_data.catalog.CountRowsParameters;
import com.a_smart_cookie.adapter.filtering_data.catalog.FilterParameters;
import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.dao.PublicationDao;
import com.a_smart_cookie.dao.ResourceReleaser;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlPublicationDao extends PublicationDao {

	@Override
	public List<Publication> findPublicationsByFilterParameters(FilterParameters filterParameters) throws DaoException {
		StringBuilder queryBuilder = getQueryWithAppliedFilterParameters(filterParameters);
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(queryBuilder.toString());
			pstmt.setString(1, filterParameters.getLanguage().name().toLowerCase());
			rs = pstmt.executeQuery();

			return extractPublications(rs);

		} catch (SQLException e) {
			throw new DaoException("Can't find all publications with query '" + queryBuilder + "'", e);
		} finally {
			ResourceReleaser.close(rs);
			ResourceReleaser.close(pstmt);
		}
	}

	private StringBuilder getQueryWithAppliedFilterParameters(FilterParameters filterParameters) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(Query.Publication.BUILDER_FIND_ALL_BY_LANGUAGE.getQuery());

		if (filterParameters.getSpecificGenre() != null) {
			queryBuilder.append(" AND genre.name = ").append(filterParameters.getSpecificGenre().name().toLowerCase());
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

	private List<Publication> extractPublications(ResultSet rs) throws SQLException {
		List<Publication> publications = new ArrayList<>();

		while (rs.next()) {
			publications.add(extractPublication(rs));
		}
		return publications;
	}

	private Publication extractPublication(ResultSet rs) throws SQLException {
		return new Publication(
				Publication.Genre.safeFromString(rs.getString(EntityColumn.Genre.NAME.getName())),
				rs.getString(EntityColumn.PublicationInfo.TITLE.getName()),
				rs.getString(EntityColumn.PublicationInfo.DESCRIPTION.getName()),
				rs.getBigDecimal(EntityColumn.Publication.PRICE_PER_MONTH.getName())
		);
	}

	@Override
	public int getTotalNumberOfRequestedQueryRows(CountRowsParameters countRowsParameters) throws DaoException {
		StringBuilder queryBuilder = new StringBuilder();

		queryBuilder.append(Query.Publication.BUILDER_GET_NUMBER_OF_ROWS_FOUNDED_BY_LANGUAGE.getQuery());

		if (countRowsParameters.getGenre() != null ) {
			queryBuilder.append(" AND genre.name = ").append(countRowsParameters.getGenre().name().toLowerCase());
		}

		if (countRowsParameters.getSearchedTitle() != null) {
			queryBuilder.append(" AND UPPER(publication_info.title) LIKE UPPER('%")
					.append(countRowsParameters.getSearchedTitle())
					.append("%') ");
		}

		queryBuilder.append(";");

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

			return numberOfRows;

		} catch (SQLException e) {
			throw new DaoException("Can't count publications with query '" + queryBuilder + "'", e);
		} finally {
			ResourceReleaser.close(rs);
			ResourceReleaser.close(pstmt);
		}
	}
}
