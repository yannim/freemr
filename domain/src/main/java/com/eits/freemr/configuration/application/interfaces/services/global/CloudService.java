package com.eits.freemr.configuration.application.interfaces.services.global;

/**
 * The Interface CloudService.
 */
public interface CloudService {

    /**
     * Creates the.
     * 
     * @param tenantIdentifier
     *            the tenant identifier
     * @param name
     *            the name
     * @param description
     *            the description
     * @return the string
     */
    String create(String tenantIdentifier, String name, String description);

    /**
     * Modify.
     * 
     * @param identifier
     *            the identifier
     * @param name
     *            the name
     * @param description
     *            the description
     */
    void modify(String identifier, String name, String description);

    /**
     * Archive.
     * 
     * @param identifier
     *            the identifier
     */
    void archive(String identifier);
}
