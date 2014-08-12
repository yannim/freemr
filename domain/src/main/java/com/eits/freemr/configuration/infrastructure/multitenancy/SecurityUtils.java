package com.eits.freemr.configuration.infrastructure.multitenancy;

import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {
    private SecurityUtils() {
    }

    public static TenantUserDetails retrieveTenantUserDetails() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails instanceof TenantUserDetails) {
            return (TenantUserDetails) userDetails;
        } else {
            return null;
        }
    }

}
