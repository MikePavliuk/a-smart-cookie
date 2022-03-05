package com.a_smart_cookie.service;

import com.a_smart_cookie.dto.user.SubscriptionStatistics;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.User;
import com.a_smart_cookie.exception.NotUpdatedResultsException;
import com.a_smart_cookie.exception.ServiceException;

/**
 * Interface for creating concrete representation of SubscriptionService.
 * Provides with ability to manage subscriptions.
 *
 */
public interface SubscriptionService {

	/**
	 * Performs subscribing user to some publication.
	 *
	 * @param user User that wants to subscribe.
	 * @param publicationId Publication's id to subscribe on.
	 * @param periodInMonths Period for subscription to be active.
	 * @return Returns updated user.
	 * @throws ServiceException Thrown when can't get publication by id, user's balance is less than needed to perform payment or can't debit funds from user's account.
	 * @throws NotUpdatedResultsException Thrown when can't update user's balance or user's subscriptions.
	 */
	User subscribeToPublication(User user, int publicationId, int periodInMonths) throws ServiceException, NotUpdatedResultsException ;

	/**
	 * Gets subscription statistics for user translated into requested language.
	 *
	 * @param user User to get statistics from.
	 * @param language Language of publications.
	 * @return Statistics of subscriptions.
	 * @throws ServiceException Thrown as wrapper for DaoException.
	 */
	SubscriptionStatistics getSubscriptionsStatistics(User user, Language language) throws ServiceException;

}
