package com.eits.freemr.configuration.rest.api.global.validation;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eits.freemr.configuration.domain.interfaces.exception.AggregateArchivedException;
import com.eits.freemr.configuration.rest.api.global.exception.TenantExistsException;
import com.eits.freemr.configuration.rest.api.global.exception.TenantNotFoundException;
import com.eits.freemr.configuration.view.global.tenant.Tenant;
import com.eits.freemr.configuration.view.global.tenant.TenantView;

@Component
@NoArgsConstructor
@RequiredArgsConstructor
public class DefaultTenantValidator implements TenantValidator {

    @Autowired
    @NonNull
    private TenantView tenantView;

    public void assertTenantAvailable(String tenantIdentifier) {
        Tenant tenant = tenantView.findByIdentifier(tenantIdentifier);
        if (tenant == null) {
            throw new TenantNotFoundException("Tenant does not exist");
        } else if (tenant.isArchived()) {
            throw new AggregateArchivedException("Tenant is already archived");
        }

    }

    public void assertTenantNameNotInUse(String name) {
        if (tenantView.findByName(name).size() > 0) {
            throw new TenantExistsException("Tenant name [" + name + "] already in use!");
        }
    }

    public void assertTenantNameNotInUseInOtherTenant(String name, String tenantId) {
        if (tenantView.isNameInUseInOtherTenant(name, tenantId)) {
            throw new TenantExistsException("Tenant name [" + name + "] already in use!");
        }
    }
}
