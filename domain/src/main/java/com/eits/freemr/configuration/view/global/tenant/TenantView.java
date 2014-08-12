package com.eits.freemr.configuration.view.global.tenant;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The Interface TenantView.
 */
public interface TenantView {

    /**
     * Find all.
     * 
     * @param exclude
     *            the exclude
     * @param pageable
     *            the pageable
     * @return the page
     */
    Page<Tenant> findAll(String exclude, Pageable pageable);

    /**
     * Find by identifier.
     * 
     * @param identifier
     *            the identifier
     * @return the tenant
     */
    Tenant findByIdentifier(String identifier);

    /**
     * Find non current tenant.
     * 
     * @param tenantId
     *            the tenant id
     * @param pageable
     *            the pageable
     * @return the page
     */
    Page<Tenant> findNonCurrentTenant(String tenantId, Pageable pageable);

    /**
     * Checks if is name in use in other tenant.
     * 
     * @param name
     *            the name
     * @param identifier
     *            the identifier
     * @return true, if is name in use in other tenant
     */
    boolean isNameInUseInOtherTenant(String name, String identifier);

    /**
     * Find by name.
     * 
     * @param name
     *            the name
     * @return the list
     */
    List<Tenant> findByName(String name);

    Page<Tenant> findAllArchived(boolean archived, Pageable pageable);
}
