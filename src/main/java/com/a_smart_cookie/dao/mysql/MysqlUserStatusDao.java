package com.a_smart_cookie.dao.mysql;

import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.dao.ResourceReleaser;
import com.a_smart_cookie.dao.UserStatusDao;
import com.a_smart_cookie.exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data access object for status related entities implemented with MySql
 */
public class MysqlUserStatusDao extends UserStatusDao {

	private static final Logger LOG = Logger.getLogger(MysqlUserStatusDao.class);

	@Override
	public int getIdByName(String name) throws DaoException {
		LOG.debug("Method starts");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(Query.UserStatus.GET_ID_BY_NAME.getQuery());
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();

			LOG.trace(pstmt);

			if (rs.next()) {
				LOG.debug("Method finished");
				return rs.getInt(EntityColumn.Role.ID.getName());
			}

			LOG.error("Result set is empty");
			throw new DaoException("Result set is empty");

		} catch (SQLException e) {
			throw new DaoException("Can't get status id", e);
		} finally {
			ResourceReleaser.close(rs);
			ResourceReleaser.close(pstmt);
		}
	}
}
