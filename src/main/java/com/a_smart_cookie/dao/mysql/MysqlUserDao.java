package com.a_smart_cookie.dao.mysql;

import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.dao.ResourceReleaser;
import com.a_smart_cookie.dao.UserDao;
import com.a_smart_cookie.entity.Role;
import com.a_smart_cookie.entity.Status;
import com.a_smart_cookie.entity.User;
import com.a_smart_cookie.entity.UserDetail;
import com.a_smart_cookie.exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data access object for User related entities implemented with MySql
 *
 */
public class MysqlUserDao extends UserDao {

	private static final Logger LOG = Logger.getLogger(MysqlUserDao.class);

	@Override
	public boolean isUserExistsByEmail(String email) throws DaoException {
		LOG.debug("Starts checking whether user exists");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(Query.User.CHECK_IF_USER_EXISTS_BY_EMAIL.getQuery());
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			rs.next();
			LOG.debug("Finished checking whether user exists");
			return rs.getBoolean(1);

		} catch (SQLException e) {
			LOG.error("Can't get user by email " + email, e);
			throw new DaoException("Can't get user by email " + email, e);
		} finally {
			ResourceReleaser.close(rs);
			ResourceReleaser.close(pstmt);
		}
	}

	@Override
	public Optional<User> getUserWithoutSubscriptionsByEmail(String email) throws DaoException {
		LOG.debug("Starts getting user");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(Query.User.GET_USER_BY_EMAIL.getQuery());
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();

			LOG.trace(pstmt);

			if (rs.next()) {
				LOG.trace("Finished getting user with result --> Found user");
				return Optional.of(extractUser(rs));
			}

			LOG.trace("Finished getting user with result --> Didn't find user");
			return Optional.empty();

		} catch (SQLException e) {
			LOG.error("Can't get user by email " + email, e);
			throw new DaoException("Can't get user by email " + email, e);
		} finally {
			ResourceReleaser.close(rs);
			ResourceReleaser.close(pstmt);
		}
	}

	@Override
	public Optional<User> insertUser(User user) throws DaoException {
		LOG.debug("Starts inserting user");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(Query.User.INSERT_USER.getQuery(), Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getEmail());
			pstmt.setBytes(2, user.getPassword());
			pstmt.setBytes(3, user.getSalt());
			pstmt.setInt(4, user.getRole().getId());
			pstmt.setInt(5, user.getStatus().getId());

			LOG.trace(pstmt);

			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					return Optional.of(User.UserBuilder.fromUser(user).withId(rs.getInt(1)).build());
				}
			}
			return Optional.empty();

		} catch (SQLException e) {
			LOG.error("Can't insert user", e);
			throw new DaoException("Can't insert user", e);
		} finally {
			ResourceReleaser.close(rs);
			ResourceReleaser.close(pstmt);
		}
	}

	/**
	 * Extracts users from ResultSet to list of users.
	 *
	 * @param rs External ResultSet
	 * @return List of users
	 */
	private List<User> extractUsers(ResultSet rs) throws SQLException {
		List<User> users = new ArrayList<>();

		while (rs.next()) {
			users.add(extractUser(rs));
		}
		return users;
	}

	/**
	 * Extracts user from ResultSet to User entity
	 *
	 * @param rs External ResultSet
	 * @return Genre enum value
	 */
	private User extractUser(ResultSet rs) throws SQLException {

		Role role = Role.valueOf(rs.getString("role_" + EntityColumn.Role.NAME.getName()).toUpperCase());

		LOG.trace(role);

		User.UserBuilder userBuilder = new User.UserBuilder(
				rs.getString(EntityColumn.User.EMAIL.getName()),
				rs.getBytes(EntityColumn.User.PASSWORD.getName()),
				rs.getBytes(EntityColumn.User.SALT.getName()),
				Status.valueOf(rs.getString("userstatus_" + EntityColumn.UserStatus.NAME.getName()).toUpperCase()),
				role);

		userBuilder.withId(rs.getInt(EntityColumn.User.ID.getName()));

		if (role == Role.SUBSCRIBER) {
			userBuilder.withUserDetail(new UserDetail(
							rs.getInt("userdetail_" + EntityColumn.UserDetail.ID),
							rs.getString(EntityColumn.UserDetail.NAME.getName()),
							rs.getString(EntityColumn.UserDetail.SURNAME.getName()),
							rs.getBigDecimal(EntityColumn.UserDetail.BALANCE.getName())));
		}

		LOG.trace(userBuilder);

		return userBuilder.build();

	}

}
