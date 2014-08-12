package com.eits.freemr.configuration.rest.api.global.exception;

import com.eits.freemr.configuration.domain.interfaces.exception.DuplicateException;

public class CloudExistsException extends DuplicateException {

    private static final long serialVersionUID = 2364508625415383133L;

    public CloudExistsException(String message) {
        super(message);
    }

}
