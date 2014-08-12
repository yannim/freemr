package com.eits.freemr.configuration.infrastructure.multitenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SchemaResolver implements CurrentTenantIdentifierResolver {

    public static final String DEFAULT_SCHEMA = "freemr";

    @Override
    public String resolveCurrentTenantIdentifier() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return DEFAULT_SCHEMA;
        }
        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return DEFAULT_SCHEMA;
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof TenantUserDetails)) {
            return DEFAULT_SCHEMA;
        }
        String tenantIdentfier = ((TenantUserDetails) principal).getTenantIdentifier();
        return tenantIdentfier == null ? DEFAULT_SCHEMA : tenantIdentfier;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }

}
