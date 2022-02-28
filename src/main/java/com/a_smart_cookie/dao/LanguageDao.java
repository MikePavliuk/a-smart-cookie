package com.a_smart_cookie.dao;

import com.a_smart_cookie.exception.DaoException;

/**
 * Interface for creating concrete representation for operating on Language entity.
 *
 */
public abstract class LanguageDao extends AbstractDao {

	/**
	 * Gets language id by name.
	 *
	 * @param name Name of language for getting id.
	 * @return Id of language.
	 */
	public abstract int getLanguageIdByName(String name) throws DaoException;

}
