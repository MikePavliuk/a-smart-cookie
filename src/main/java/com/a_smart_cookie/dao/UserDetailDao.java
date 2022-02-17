package com.a_smart_cookie.dao;

import com.a_smart_cookie.entity.UserDetail;
import com.a_smart_cookie.exception.DaoException;

import java.util.Optional;

/**
 * Interface for creating concrete representation for operating on UserDetail entity.
 *
 */
public abstract class UserDetailDao extends AbstractDao {

	/**
	 * Insert UserDetail record into db and returns its set id.
	 *
	 * @param userDetail UserDetail information for creating record in db.
	 * @param userId User id of this detail info.
	 * @return Generated id for inserted UserDetail or empty if something went wrong.
	 */
	public abstract Optional<UserDetail> insertUserDetail(UserDetail userDetail, int userId) throws DaoException;

}
