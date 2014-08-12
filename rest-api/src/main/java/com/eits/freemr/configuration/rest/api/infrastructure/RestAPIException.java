package com.eits.freemr.configuration.rest.api.infrastructure;

/**
 * The Class RestAPIException.
 */
public class RestAPIException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8795851338173131711L;

    /**
     * Instantiates a new rest api exception.
     *
     * @param message the message
     */
    public RestAPIException(String message) {
        super(message);
    }

}
