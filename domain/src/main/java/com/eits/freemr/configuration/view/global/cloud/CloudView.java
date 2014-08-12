package com.eits.freemr.configuration.view.global.cloud;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The Interface CloudView.
 */
public interface CloudView {

    /**
     * List all.
     * 
     * @param pageable
     *            the pageable
     * @return the page
     */
    Page<Cloud> listAll(Pageable pageable);

    /**
     * List by tenant identifier.
     * 
     * @param tenantIdentifier
     *            the tenant identifier
     * @param pageable
     *            the pageable
     * @return the page
     */
    Page<Cloud> listByTenantIdentifier(String tenantIdentifier, Pageable pageable);

    /**
     * Find one.
     * 
     * @param identifier
     *            the identifier
     * @return the cloud
     */
    Cloud findOne(String identifier);

    /**
     * Find by name.
     * 
     * @param name
     *            the name
     * @return the cloud
     */
    Cloud findByName(String name);

    /**
     * Checks if is name used in other cloud.
     * 
     * @param identifier
     *            the identifier
     * @param name
     *            the name
     * @return true, if is name used in other cloud
     */
    boolean isNameUsedInOtherCloud(String identifier, String name);

}
