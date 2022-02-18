package com.a_smart_cookie.util.payment;

import com.a_smart_cookie.exception.PaymentException;

import java.math.BigDecimal;

/**
 * Maintains a reference to one of the concrete strategies and communicates with this object only via the strategy interface.
 *
 */
public class PaymentContext {
	private final PaymentStrategy paymentStrategy;

	public PaymentContext(PaymentStrategy paymentStrategy) {
		if (paymentStrategy == null) {
			throw new IllegalArgumentException("Payment strategy can't be null");
		}

		this.paymentStrategy = paymentStrategy;
	}

	/**
	 * Main method for performing payment on set strategy.
	 *
	 * @param paymentAmount amount of money for payment
	 * @return Whether transaction was success or no.
	 */
	public boolean performPayment(BigDecimal paymentAmount) throws PaymentException {
		return paymentStrategy.pay(paymentAmount);
	}
}
