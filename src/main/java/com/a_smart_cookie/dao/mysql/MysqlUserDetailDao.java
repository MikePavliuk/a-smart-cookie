package com.a_smart_cookie.dao.mysql;

import com.a_smart_cookie.dao.ResourceReleaser;
import com.a_smart_cookie.dao.UserDetailDao;
import com.a_smart_cookie.entity.UserDetail;
import com.a_smart_cookie.exception.DaoException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/**
 * Data access object for UserDetail related entities implemented with MySql
 *
 */
public class MysqlUserDetailDao extends UserDetailDao {

	private static final Logger LOG = Logger.getLogger(MysqlUserDetailDao.class);

	@Override
	public Optional<UserDetail> insertUserDetail(UserDetail userDetail, int userId) throws DaoException {
		LOG.debug("Starts method");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(Query.UserDetail.INSERT_USER_DETAIL.getQuery(), Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, userDetail.getFirstName());
			pstmt.setString(2, userDetail.getLastName());
			pstmt.setInt(3, userId);

			LOG.trace(pstmt);

			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					LOG.debug("Finished method with inserted user detail");
					return Optional.of(new UserDetail(userId, userDetail.getFirstName(), userDetail.getLastName(), BigDecimal.ZERO));
				}
			}

			LOG.debug("Finished method with not inserted user detail");
			return Optional.empty();

		} catch (SQLException e) {
			LOG.error("Can't insert user detail", e);
			throw new DaoException("Can't insert user detail", e);
		} finally {
			ResourceReleaser.close(rs);
			ResourceReleaser.close(pstmt);
		}
	}

}
