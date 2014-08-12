package com.eits.freemr.configuration.rest.api.global.tenant;

import java.util.Collection;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;

import com.eits.freemr.configuration.infrastructure.multitenancy.TenantUserDetails;

@RequiredArgsConstructor
public class DummyTenantUserDetails implements TenantUserDetails {
    private static final long serialVersionUID = -7007064199281428117L;

    private final String tenantIdentifier;

    @Setter
    private String userName;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getTenantIdentifier() {
        return tenantIdentifier;
    }

}
