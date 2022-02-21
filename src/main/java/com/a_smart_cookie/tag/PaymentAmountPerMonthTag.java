package com.a_smart_cookie.tag;

import com.a_smart_cookie.dto.user.SubscriptionWithPublicationInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Custom jstl tag for getting payment amount per month.
 *
 */
public class PaymentAmountPerMonthTag {

	/**
	 * Gets total payment amount per month by subscriptions.
	 *
	 * @param subscriptions List of subscriptions.
	 * @return Sum of payment per month values.
	 */
	public static BigDecimal getTotalPaymentAmountPerMonth(List<SubscriptionWithPublicationInfo> subscriptions) {
		return subscriptions.stream()
				.map(s -> s.getPublication().getPricePerMonth())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
