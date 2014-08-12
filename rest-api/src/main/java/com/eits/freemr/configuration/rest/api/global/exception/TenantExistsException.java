package com.eits.freemr.configuration.rest.api.global.exception;

import com.eits.freemr.configuration.domain.interfaces.exception.DuplicateException;


/**
 * The Class TenantExistsException.
 */
public class TenantExistsException extends DuplicateException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 937934389471489335L;

    /**
     * Instantiates a new tenant exists exception.
     * 
     * @param message
     *            the message
     */
    public TenantExistsException(String message) {
        super(message);
    }

}
