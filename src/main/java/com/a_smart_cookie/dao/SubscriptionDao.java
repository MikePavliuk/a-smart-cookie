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
	 * @throws DaoException Exception on db layer.
	 */
	public abstract List<Subscription> getSubscriptionsByUserId(int id) throws DaoException;

	/**
	 * Insert subscription.
	 *
	 * @param userId User's id.
	 * @param publicationId Publication's id.
	 * @return Optional of subscription if subscription was correctly inserted, otherwise - empty optional.
	 */
	public abstract boolean insertSubscription(int userId, int publicationId) throws DaoException;

}
