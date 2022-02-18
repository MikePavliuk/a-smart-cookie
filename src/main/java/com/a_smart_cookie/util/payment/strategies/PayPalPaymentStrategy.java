package com.a_smart_cookie.util.payment.strategies;

import com.a_smart_cookie.exception.PaymentException;
import com.a_smart_cookie.util.payment.PaymentStrategy;

import java.math.BigDecimal;

/**
 * Concrete implementation of payment strategy used to pay via PayPal
 *
 */
public final class PayPalPaymentStrategy implements PaymentStrategy {

	@Override
	public boolean pay(BigDecimal paymentAmount) throws PaymentException {

		if (paymentAmount == null) {
			throw new PaymentException("Payment amount can't be null");
		}

		if (paymentAmount.signum() <= 0) {
			throw new PaymentException("Can't make paypal payment transaction with payment amount of " + paymentAmount);
		}

		return true;
	}
}
