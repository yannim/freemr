package com.eits.freemr.configuration.view.global.tenant.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TenantUserRepository extends PagingAndSortingRepository<TenantUser, String> {

    TenantUser findByAccount(String account);

    Page<TenantUser> findByTenantIdentifier(String tenantIdentifier, Pageable pageable);

}
