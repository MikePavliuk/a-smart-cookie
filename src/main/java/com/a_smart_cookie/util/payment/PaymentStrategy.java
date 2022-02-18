package com.a_smart_cookie.util.payment;

import com.a_smart_cookie.exception.PaymentException;

import java.math.BigDecimal;

/**
 * Main interface for implementing Strategy pattern for payment methods.
 *
 */
public interface PaymentStrategy {

	/**
	 * Performs payment.
	 * @param paymentAmount Money to be got from wallet.
	 * @return Whether payment transaction was success or no.
	 */
	boolean pay(BigDecimal paymentAmount) throws PaymentException;
}
