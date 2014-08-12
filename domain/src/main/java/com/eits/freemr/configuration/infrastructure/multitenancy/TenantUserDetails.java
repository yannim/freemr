package com.eits.freemr.configuration.infrastructure.multitenancy;

import org.springframework.security.core.userdetails.UserDetails;

public interface TenantUserDetails extends UserDetails {

    String getTenantIdentifier();

}
