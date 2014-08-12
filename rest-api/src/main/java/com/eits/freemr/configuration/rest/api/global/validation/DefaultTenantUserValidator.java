package com.eits.freemr.configuration.rest.api.global.validation;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eits.freemr.configuration.rest.api.exception.TenantUserExistsException;
import com.eits.freemr.configuration.view.global.tenant.user.TenantUser;
import com.eits.freemr.configuration.view.global.tenant.user.TenantUserView;

@Component
@NoArgsConstructor
@RequiredArgsConstructor
public class DefaultTenantUserValidator implements TenantUserValidator {

    @Autowired
    @NonNull
    private TenantUserView tenantUserView;

    public void assertAccountNotExists(String account) {
        TenantUser tenantUser = tenantUserView.findByAccount(account);
        if (tenantUser != null) {
            throw new TenantUserExistsException("User [" + account + "] already exists!");
        }
    }
}
