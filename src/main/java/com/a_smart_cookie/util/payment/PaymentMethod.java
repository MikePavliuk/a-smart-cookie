package com.a_smart_cookie.util.payment;

/**
 * Defines possible methods of payment to the user.
 *
 */
public enum PaymentMethod {
	PAYPAL("PayPal"),
	CREDIT_CARD("Credit Card");

	private final String name;

	PaymentMethod(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
