package com.a_smart_cookie.tag;

import com.a_smart_cookie.entity.Subscription;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

public class SubscriptionCheckerTagTesting {

	@Test
	void givenNullSubscriptions_whenContains_thenThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class,
				() -> SubscriptionCheckerTag.contains(null, anyInt()));
	}

	@Test
	void givenNullPublicationId_whenContains_thenThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class,
				() -> SubscriptionCheckerTag.contains(new ArrayList<>(), null));
	}

	@Test
	void givenSubscriptionsThatContainsRequestedPublicationId_whenContains_thenReturnTrue() {
		List<Subscription> subscriptions = new ArrayList<>();
		Integer publicationId = 1;
		subscriptions.add(new Subscription(publicationId, Date.valueOf(LocalDate.now()), 1));

		assertTrue(SubscriptionCheckerTag.contains(subscriptions, publicationId));
	}

	@Test
	void givenSubscriptionsThatNOTContainsRequestedPublicationId_whenContains_thenReturnFalse() {
		List<Subscription> subscriptions = new ArrayList<>();
		Integer publicationId = 1;
		subscriptions.add(new Subscription(publicationId, Date.valueOf(LocalDate.now()), 1));

		assertFalse(SubscriptionCheckerTag.contains(subscriptions, 2));
	}

}
