package com.a_smart_cookie.exception;

/**
 * Exception used for identifying exceptions occurred while performing payment.
 *
 */
public class PaymentException extends Exception {

	private static final long serialVersionUID = -9024503477540556299L;

	public PaymentException() {
	}

	public PaymentException(String message) {
		super(message);
	}

	public PaymentException(String message, Throwable cause) {
		super(message, cause);
	}

	public PaymentException(Throwable cause) {
		super(cause);
	}

}
