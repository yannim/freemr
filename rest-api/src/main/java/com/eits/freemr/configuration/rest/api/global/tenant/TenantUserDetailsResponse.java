package com.eits.freemr.configuration.rest.api.global.tenant;

import java.util.Collection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
@Getter
public class TenantUserDetailsResponse {
    private final String tenantIdentifier;

    private final String username;

    private final boolean accountNonExpired;

    private final boolean accountNonLocked;

    private final boolean credentialsNonExpired;

    private final boolean enabled;

    private final Collection<? extends GrantedAuthority> authorities;

}
