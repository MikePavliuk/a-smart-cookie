package com.a_smart_cookie.dao;

import com.a_smart_cookie.entity.User;
import com.a_smart_cookie.exception.DaoException;

import java.util.List;
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
	public abstract Optional<User> getUserWithoutSubscriptionsByEmail(String email) throws DaoException;

	/**
	 * Insert user into db and returns its set id.
	 *
	 * @param user User information for creating record in db.
	 * @return Generated id for inserted user or empty if something went wrong.
	 */
	public abstract Optional<User> createUser(User user) throws DaoException;

	/**
	 * Gets limited number of subscribers.
	 *
	 * @return List of subscribers.
	 */
	public abstract List<User> getSubscribersWithLimit(int offset, int itemsPerPage) throws DaoException;

	/**
	 * Change user status.
	 *
	 * @param userId Users id.
	 * @param statusName Updated status name.
	 * @return Whether user status was changes.
	 */
	public abstract boolean changeUserStatus(int userId, String statusName) throws DaoException;

	/**
	 * Method for getting number of subscribers.
	 *
	 * @return Number of founded subscribers.
	 */
	public abstract int getTotalNumberOfSubscribers() throws DaoException;
}
