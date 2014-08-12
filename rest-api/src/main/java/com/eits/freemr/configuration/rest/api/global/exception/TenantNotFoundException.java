package com.eits.freemr.configuration.rest.api.global.exception;

import com.eits.freemr.configuration.domain.interfaces.exception.NotFoundException;


/**
 * The Class TenantNotFoundException.
 */
public class TenantNotFoundException extends NotFoundException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6476898210687442719L;

    /**
     * Instantiates a new tenant not found exception.
     * 
     * @param message
     *            the message
     */
    public TenantNotFoundException(String message) {
        super(message);
    }

}
