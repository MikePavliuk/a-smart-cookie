package com.a_smart_cookie.dao;

import com.a_smart_cookie.exception.DaoException;

/**
 * Interface for creating concrete representation for operating on Status entity.
 *
 */
public abstract class UserStatusDao extends AbstractDao {

	/**
	 * Gets status id by name.
	 *
	 * @param name Name of status for getting id.
	 * @return Id of status.
	 * @throws DaoException Occurred when something went wrong on JDBC layer.
	 */
	public abstract int getIdByName(String name) throws DaoException;


}
