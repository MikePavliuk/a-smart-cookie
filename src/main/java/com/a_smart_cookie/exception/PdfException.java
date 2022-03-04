package com.a_smart_cookie.exception;

/**
 * Exception used for identifying exceptions occurred while downloading pdf-file.
 *
 */
public class PdfException extends Exception {

	private static final long serialVersionUID = 1212601851700455451L;

	public PdfException() {
	}

	public PdfException(String message) {
		super(message);
	}

	public PdfException(String message, Throwable cause) {
		super(message, cause);
	}

	public PdfException(Throwable cause) {
		super(cause);
	}

}
