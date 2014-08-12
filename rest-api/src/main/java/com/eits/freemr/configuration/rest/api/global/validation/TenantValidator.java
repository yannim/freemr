package com.eits.freemr.configuration.rest.api.global.validation;

/**
 * The Interface TenantValidator.
 */
public interface TenantValidator {

    /**
     * Assert tenant available.
     * 
     * @param tenantIdentifier
     *            the tenant identifier
     */
    void assertTenantAvailable(String tenantIdentifier);

    /**
     * Assert tenant name not in use.
     * 
     * @param name
     *            the name
     */
    void assertTenantNameNotInUse(String name);

    /**
     * Assert tenant name not in use in other tenant.
     * 
     * @param name
     *            the name
     * @param tenantId
     *            the tenant id
     */
    void assertTenantNameNotInUseInOtherTenant(String name, String tenantId);
}
