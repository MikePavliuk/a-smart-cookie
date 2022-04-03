package com.a_smart_cookie.dao;

import com.a_smart_cookie.exception.DaoException;

/**
 * Interface for creating concrete representation for operating on Role entity.
 *
 */
public abstract class RoleDao extends AbstractDao {

	/**
	 * Gets role id by name.
	 *
	 * @param name Name of role for getting id.
	 * @return Id of role.
	 * @throws DaoException Occurred when something went wrong on JDBC layer.
	 */
	public abstract int getIdByName(String name) throws DaoException;

}
