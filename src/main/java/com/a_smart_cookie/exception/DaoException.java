package com.a_smart_cookie.exception;

/**
 * Exception used for identifying exceptions occurred on dao layer.
 *
 */
public class DaoException extends Exception {

    private static final long serialVersionUID = -8253938388205140011L;

    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

}
