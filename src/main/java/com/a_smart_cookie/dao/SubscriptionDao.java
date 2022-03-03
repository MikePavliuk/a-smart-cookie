package com.a_smart_cookie.dao;

import com.a_smart_cookie.entity.Subscription;
import com.a_smart_cookie.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface for creating concrete representation for operating on Subscription entity.
 *
 */
public abstract class SubscriptionDao extends AbstractDao {

	/**
	 * Gets active subscriptions by user id.
	 *
	 * @param id Users id.
	 * @return List of subscriptions.
	 */
	public abstract List<Subscription> getActiveSubscriptionsByUserId(int id) throws DaoException;

	/**
	 * Insert subscription.
	 *
	 * @param userId User's id.
	 * @param publicationId Publication's id.
	 * @param periodInMonths Period for subscription being active.
	 * @return Whether subscription was correctly inserted.
	 */
	public abstract boolean insertSubscription(int userId, int publicationId, int periodInMonths) throws DaoException;


	/**
	 * Gets number of all inactive subscriptions of user by his id.
	 *
	 * @param userId Users id.
	 * @return Number of subscriptions.
	 */
	public abstract int getNumberOfInactiveSubscriptionsByUserId(int userId) throws DaoException;

	/**
	 * Gets number of all active subscriptions of user by his id.
	 *
	 * @param userId Users id.
	 * @return Number of subscriptions.
	 */
	public abstract int getNumberOfActiveSubscriptionsByUserId(int userId) throws DaoException;

	/**
	 * Gets total amount of spend money by user id in the service.
	 *
	 * @param userId User's id.
	 * @return Spent money value.
	 */
	public abstract BigDecimal getTotalAmountOfSpentMoneyByUserId(int userId) throws DaoException;

}
