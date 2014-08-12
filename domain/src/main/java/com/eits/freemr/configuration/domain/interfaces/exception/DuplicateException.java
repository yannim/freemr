package com.eits.freemr.configuration.domain.interfaces.exception;

public abstract class DuplicateException extends RuntimeException {

    private static final long serialVersionUID = -923816258386737599L;

    protected DuplicateException(String message) {
        super(message);
    }

    protected DuplicateException(Throwable cause) {
        super(cause);
    }

    protected DuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

}
