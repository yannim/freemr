package com.eits.freemr.configuration.view.global.tenant;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * The Interface TenantRepository.
 */
public interface TenantRepository extends PagingAndSortingRepository<Tenant, String> {

    /**
     * Find by identifier not.
     * 
     * @param tenantId
     *            the tenant id
     * @param pageable
     *            the pageable
     * @return the page
     */
    Page<Tenant> findByIdentifierNot(String tenantId, Pageable pageable);

    /**
     * Find by name.
     * 
     * @param tenantName
     *            the tenant name
     * @return the list
     */
    List<Tenant> findByName(String tenantName);

    /**
     * Find by name and identifier not.
     * 
     * @param name
     *            the name
     * @param identifier
     *            the identifier
     * @return the list
     */
    List<Tenant> findByNameAndIdentifierNot(String name, String identifier);

    Page<Tenant> findByArchived(boolean archived, Pageable pageable);

}
