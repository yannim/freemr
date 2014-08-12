package com.eits.freemr.configuration.rest.api.global.validation;

/**
 * The Interface TenantUserValidator.
 */
public interface TenantUserValidator {

    /**
     * Assert account not exists.
     * 
     * @param account
     *            the account
     */
    void assertAccountNotExists(String account);
}
