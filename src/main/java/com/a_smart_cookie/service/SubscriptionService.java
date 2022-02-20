package com.a_smart_cookie.service;

import com.a_smart_cookie.dto.SubscriptionWithPublicationInfo;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.User;
import com.a_smart_cookie.exception.NotUpdatedResultsException;
import com.a_smart_cookie.exception.ServiceException;

import java.util.List;

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
	 * @return Returns updated user.
	 * @throws ServiceException Occurred on dao layer
	 * @throws NotUpdatedResultsException Occurred when updated results can be got.
	 */
	User subscribeToPublication(User user, int publicationId) throws ServiceException, NotUpdatedResultsException;

	/**
	 * Gets subscriptions of user.
	 *
	 * @param user User.
	 * @param language Language for publications.
	 * @return List of subscriptions.
	 */
	List<SubscriptionWithPublicationInfo> getSubscriptionsWithFullInfoByUserAndLanguage(User user, Language language);

	/**
	 * Performs unsubscribing user from publication.
	 *
	 * @param user User that wants to unsubscribe.
	 * @param publicationId Publication's id to unsubscribe from.
	 * @return Returns updated user.
	 * @throws ServiceException Occurred on dao layer
	 * @throws NotUpdatedResultsException Occurred when updated results can be got.
	 */
	User unsubscribeFromPublication(User user, int publicationId) throws ServiceException, NotUpdatedResultsException;

}
