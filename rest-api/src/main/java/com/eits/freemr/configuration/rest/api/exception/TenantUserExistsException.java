package com.eits.freemr.configuration.rest.api.exception;

import com.eits.freemr.configuration.domain.interfaces.exception.DuplicateException;

public class TenantUserExistsException extends DuplicateException {

    private static final long serialVersionUID = 8030266698986096400L;

    public TenantUserExistsException(String message) {
        super(message);
    }

}
