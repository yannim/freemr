package com.eits.freemr.configuration.application.tenantuser.interceptor;

import java.util.Collections;

import org.axonframework.commandhandling.CommandDispatchInterceptor;
import org.axonframework.commandhandling.CommandMessage;

import com.eits.freemr.configuration.infrastructure.multitenancy.SecurityUtils;
import com.eits.freemr.configuration.infrastructure.multitenancy.TenantUserDetails;

public class TenantUserInterceptor implements CommandDispatchInterceptor {

    private static final String TENANT_IDENTIFIER_KEY = "tenantIdentifier";

    @Override
    public CommandMessage<?> handle(CommandMessage<?> commandMessage) {
        TenantUserDetails tenantUserDetails = SecurityUtils.retrieveTenantUserDetails();
        if (tenantUserDetails == null) {
            return commandMessage;
        }
        return commandMessage.andMetaData(Collections.singletonMap(TENANT_IDENTIFIER_KEY, tenantUserDetails.getTenantIdentifier()));
    }

}
