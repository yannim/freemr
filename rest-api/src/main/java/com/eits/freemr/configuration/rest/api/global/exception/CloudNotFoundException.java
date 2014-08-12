package com.eits.freemr.configuration.rest.api.global.exception;

import com.eits.freemr.configuration.domain.interfaces.exception.NotFoundException;


public class CloudNotFoundException extends NotFoundException {

    private static final long serialVersionUID = -3140461121966471155L;

    public CloudNotFoundException(String message) {
        super(message);
    }

}
