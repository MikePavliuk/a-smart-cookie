package com.a_smart_cookie.exception;

/**
 * Exception used for identifying exceptions occurred on service layer.
 *
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = 8181586035433260208L;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

}
