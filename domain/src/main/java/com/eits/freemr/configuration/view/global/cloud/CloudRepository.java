package com.eits.freemr.configuration.view.global.cloud;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * The Interface CloudRepository.
 */
public interface CloudRepository extends PagingAndSortingRepository<Cloud, String> {

    /**
     * Find by tenant identifier.
     * 
     * @param tenantIdentifier
     *            the tenant identifier
     * @param pageable
     *            the pageable
     * @return the page
     */
    Page<Cloud> findByTenantIdentifier(String tenantIdentifier, Pageable pageable);

    /**
     * Find by name and identifier not.
     * 
     * @param name
     *            the name
     * @param identifier
     *            the identifier
     * @return the collection
     */
    Collection<Cloud> findByNameAndIdentifierNot(String name, String identifier);

    /**
     * Find one by name.
     * 
     * @param name
     *            the name
     * @return the cloud
     */
    Cloud findOneByName(String name);

}
