package com.eits.freemr.configuration.application.interfaces.services;

/**
 * The Interface TenantService.
 */
public interface TenantService {

    /**
     * Creates the.
     * 
     * @param name
     *            the name
     * @param description
     *            the description
     * @param organizationName
     *            the organization name
     * @param contactInformation
     *            the contact information
     * @return the string
     */
    String create(String name, String description, String organizationName, String contactInformation);

    /**
     * Modify.
     * 
     * @param identifier
     *            the identifier
     * @param name
     *            the name
     * @param description
     *            the description
     * @param organizationName
     *            the organization name
     * @param contactInformation
     *            the contact information
     */
    void modify(String identifier, String name, String description, String organizationName, String contactInformation);

    /**
     * Delete.
     * 
     * @param identifier
     *            the identifier
     */
    void delete(String identifier);

}
