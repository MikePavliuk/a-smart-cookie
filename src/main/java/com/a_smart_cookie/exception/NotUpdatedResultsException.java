package com.a_smart_cookie.exception;

public class NotUpdatedResultsException extends RuntimeException {
	private static final long serialVersionUID = -3930144120590525753L;

	public NotUpdatedResultsException() {
	}

	public NotUpdatedResultsException(String message) {
		super(message);
	}

	public NotUpdatedResultsException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotUpdatedResultsException(Throwable cause) {
		super(cause);
	}

}
