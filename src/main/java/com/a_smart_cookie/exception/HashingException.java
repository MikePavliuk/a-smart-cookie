package com.a_smart_cookie.exception;

/**
 * Exception used for identifying exceptions occurred while hashing.
 *
 */
public class HashingException extends Exception {

    private static final long serialVersionUID = 8696135517861208429L;

    public HashingException() {
    }

    public HashingException(String message) {
        super(message);
    }

    public HashingException(String message, Throwable cause) {
        super(message, cause);
    }

    public HashingException(Throwable cause) {
        super(cause);
    }

}
