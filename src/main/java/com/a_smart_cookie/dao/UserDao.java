package com.a_smart_cookie.dao;

import com.a_smart_cookie.entity.User;
import com.a_smart_cookie.exception.DaoException;

import java.util.Optional;

/**
 * Interface for creating concrete representation for operating on User entity.
 *
 */
public abstract class UserDao extends AbstractDao {

	/**
	 * Checks whether user already exist by email.
	 *
	 * @param email Email value to search by.
	 * @return Boolean value whether user already exist.
	 * @throws DaoException Exception on db layer.
	 */
	public abstract boolean isUserExistsByEmail(String email) throws DaoException;

	/**
	 * Gets user by email if he exists, otherwise returns empty optional.
	 *
	 * @param email Email value to search by.
	 * @return User without subscriptions if he exists - otherwise empty optional.
	 * @throws DaoException Exception on db layer.
	 */
	public abstract Optional<User> getUserByEmail(String email) throws DaoException;

	/**
	 * Insert user into db and returns its set id.
	 *
	 * @param user User information for creating record in db.
	 * @return Generated id for inserted user or empty if something went wrong.
	 */
	public abstract Optional<User> insertUser(User user) throws DaoException;

}
