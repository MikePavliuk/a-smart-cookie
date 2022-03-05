package com.a_smart_cookie.dao.mysql;

import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.dao.LanguageDao;
import com.a_smart_cookie.dao.ResourceReleaser;
import com.a_smart_cookie.exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data access object for language related entities implemented with MySql
 */
public class MysqlLanguageDao extends LanguageDao {

	private static final Logger LOG = Logger.getLogger(MysqlLanguageDao.class);

	@Override
	public int getLanguageIdByName(String name) throws DaoException {
		LOG.debug("Method starts");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(Query.Language.GET_ID_BY_NAME.getQuery());
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();

			LOG.trace(pstmt);

			if (rs.next()) {
				LOG.debug("Method finished");
				return rs.getInt(EntityColumn.Genre.ID.getName());
			}

			LOG.error("Result set is empty");
			throw new DaoException("Result set is empty");

		} catch (SQLException e) {
			throw new DaoException("Can't get language id", e);
		} finally {
			ResourceReleaser.close(rs);
			ResourceReleaser.close(pstmt);
		}
	}

}
