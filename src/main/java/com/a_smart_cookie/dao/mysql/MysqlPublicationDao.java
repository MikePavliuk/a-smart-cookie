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

				Publication.Genre genre =  Publication.Genre.fromString(rs.getString(EntityColumn.Genre.NAME.getName()));
				publications.add(
						new Publication(
								genre,
								rs.getString(EntityColumn.PublicationInfo.TITLE.getName()),
								rs.getString(EntityColumn.PublicationInfo.DESCRIPTION.getName()),
								rs.getBigDecimal(EntityColumn.Publication.PRICE_PER_MONTH.getName())
						));
			}

			return publications;

		} catch (SQLException e) {
			throw new DaoException("Exception occurred while finding all publications in " + language, e);
		} finally {
			ResourceReleaser.close(rs);
			ResourceReleaser.close(pstmt);
		}
	}

}
