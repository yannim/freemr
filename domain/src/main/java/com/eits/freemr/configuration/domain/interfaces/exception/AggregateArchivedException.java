package com.eits.freemr.configuration.domain.interfaces.exception;

public class AggregateArchivedException extends RuntimeException {
    private static final long serialVersionUID = 4701122822814688053L;

    public AggregateArchivedException(String message) {
        super(message);
    }

    public AggregateArchivedException(Throwable cause) {
        super(cause);
    }

    public AggregateArchivedException(String message, Throwable cause) {
        super(message, cause);
    }

}
