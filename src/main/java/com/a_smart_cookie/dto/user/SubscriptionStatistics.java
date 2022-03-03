package com.a_smart_cookie.dto.user;

import java.math.BigDecimal;
import java.util.List;

/**
 * Data transfer object for holding all necessary statistics about user's subscriptions.
 *
 */
public class SubscriptionStatistics {
	private final List<SubscriptionWithPublicationInfo> activeSubscriptions;
	private final BigDecimal totalSpentMoney;
	private final int numberOfInactiveSubscriptions;

	public SubscriptionStatistics(List<SubscriptionWithPublicationInfo> activeSubscriptions, int numberOfInactiveSubscriptions, BigDecimal totalSpentMoney) {
		this.activeSubscriptions = activeSubscriptions;
		this.numberOfInactiveSubscriptions = numberOfInactiveSubscriptions;
		this.totalSpentMoney = totalSpentMoney;
	}

	public List<SubscriptionWithPublicationInfo> getActiveSubscriptions() {
		return activeSubscriptions;
	}

	public BigDecimal getTotalSpentMoney() {
		return totalSpentMoney;
	}

	public int getNumberOfInactiveSubscriptions() {
		return numberOfInactiveSubscriptions;
	}

	@Override
	public String toString() {
		return "SubscriptionStatistics{" +
				"activeSubscriptions=" + activeSubscriptions +
				", totalSpentMoney=" + totalSpentMoney +
				", numberOfTimeoutSubscriptions=" + numberOfInactiveSubscriptions +
				'}';
	}
}
