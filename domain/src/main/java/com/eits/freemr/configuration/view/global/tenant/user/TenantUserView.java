package com.eits.freemr.configuration.view.global.tenant.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TenantUserView {

    TenantUser findByAccount(String account);

    Page<TenantUser> all(Pageable pageable);

    Page<TenantUser> listByTenantIdentifier(String tenantIdentifier, Pageable pageable);

}
