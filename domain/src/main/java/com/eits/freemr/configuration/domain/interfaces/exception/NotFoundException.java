package com.eits.freemr.configuration.domain.interfaces.exception;

public abstract class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = -6073687713106326984L;

    protected NotFoundException(String message) {
        super(message);
    }

    protected NotFoundException(Throwable cause) {
        super(cause);
    }

    protected NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
