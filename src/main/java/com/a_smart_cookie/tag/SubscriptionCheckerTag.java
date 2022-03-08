package com.a_smart_cookie.tag;

import com.a_smart_cookie.entity.Subscription;

import java.util.List;

/**
 * Custom jstl tag for checking whether publication in subscriptions.
 *
 */
public class SubscriptionCheckerTag {

	/**
	 * Check whether publication is in subscriptions.
	 *
	 * @param subscriptions List of subscriptions.
	 * @param publicationId Publications id.
	 * @return Is publication in subscription list.
	 */
	public static boolean contains(List<Subscription> subscriptions, Integer publicationId) {
		if (subscriptions == null || publicationId == null) {
			throw new IllegalArgumentException("Parameters can't be null");
		}

		return subscriptions.stream().anyMatch(subscription -> subscription.getPublicationId().equals(publicationId));
	}

}
