package com.eits.freemr.configuration.rest.api.global.validation;

/**
 * The Interface CloudValidator.
 */
public interface CloudValidator {

    /**
     * Assert cloud available.
     * 
     * @param identifier
     *            the identifier
     */
    void assertCloudAvailable(String identifier);

    /**
     * Assert cloud name not in use.
     * 
     * @param name
     *            the name
     */
    void assertCloudNameNotInUse(String name);

    /**
     * Assert cloud name not used by other cloud.
     * 
     * @param identifier
     *            the identifier
     * @param name
     *            the name
     */
    void assertCloudNameNotUsedByOtherCloud(String identifier, String name);
}
