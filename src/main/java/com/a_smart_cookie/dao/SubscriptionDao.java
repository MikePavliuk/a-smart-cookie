package com.a_smart_cookie.dao;

import com.a_smart_cookie.entity.Subscription;
import com.a_smart_cookie.exception.DaoException;

import java.util.List;

/**
 * Interface for creating concrete representation for operating on Subscription entity.
 *
 */
public abstract class SubscriptionDao extends AbstractDao {

	/**
	 * Gets subscriptions by user id.
	 *
	 * @param id Users id.
	 * @return List of subscriptions.
	 */
	public abstract List<Subscription> getSubscriptionsByUserId(int id) throws DaoException;

	/**
	 * Insert subscription.
	 *
	 * @param userId User's id.
	 * @param publicationId Publication's id.
	 * @return Whether subscription was correctly inserted.
	 */
	public abstract boolean insertSubscription(int userId, int publicationId) throws DaoException;


	/**
	 * Removes subscription from user.
	 *
	 * @param userId Id of user.
	 * @param publicationId Publication's id.
	 * @return Whether subscription was correctly deleted.
	 */
	public abstract boolean removeSubscriptions(int userId, int publicationId) throws DaoException;

	/**
	 * Gets number of active subscriptions of user by his id.
	 *
	 * @param userId Users id.
	 * @return Number of subscriptions.
	 */
	public abstract int getNumberOfSubscriptionsByUserId(int userId) throws DaoException;

}
