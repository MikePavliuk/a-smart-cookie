package com.a_smart_cookie.tag;

import com.a_smart_cookie.entity.Subscription;

import java.util.List;

public class SubscriptionCheckerTag {

	public static boolean contains(List<Subscription> subscriptions, Integer publicationId) {
		return subscriptions.stream().anyMatch(subscription -> subscription.getPublicationId().equals(publicationId));
	}

}
