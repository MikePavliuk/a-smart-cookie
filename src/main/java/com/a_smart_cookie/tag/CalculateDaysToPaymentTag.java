package com.a_smart_cookie.tag;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Custom jstl tag for calculating days to next payment.
 *
 */
public class CalculateDaysToPaymentTag {

	private static final int PAYMENT_STEP_IN_DAYS = 31;

	/**
	 * Gets days to next payment.
	 *
	 * @param startDate Start date of subscription.
	 * @return Number of days to next payment.
	 */
	public static long getDaysToPayment(LocalDate startDate) {
		long totalDaysBetween = DAYS.between(startDate, LocalDate.now());
		long daysToPayment = Math.abs(totalDaysBetween - PAYMENT_STEP_IN_DAYS) % PAYMENT_STEP_IN_DAYS;

		if (daysToPayment == 0) {
			return PAYMENT_STEP_IN_DAYS;
		}

		return daysToPayment;
	}

}
