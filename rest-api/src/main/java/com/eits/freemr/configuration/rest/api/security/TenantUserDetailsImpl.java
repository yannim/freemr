package com.eits.freemr.configuration.rest.api.security;

import java.util.Collection;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.eits.freemr.configuration.infrastructure.multitenancy.TenantUserDetails;

@RequiredArgsConstructor
public class TenantUserDetailsImpl implements TenantUserDetails {
    private static final long serialVersionUID = 1463255696674579653L;

    private final UserDetails userDetails;

    private final String tenantId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public String getPassword() {
        return userDetails.getPassword();
    }

    @Override
    public String getUsername() {
        return userDetails.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return userDetails.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return userDetails.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userDetails.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return userDetails.isEnabled();
    }

    @Override
    public String getTenantIdentifier() {
        return this.tenantId;
    }

}
