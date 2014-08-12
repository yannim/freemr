package com.eits.freemr.configuration.rest.api.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

import com.eits.freemr.configuration.view.global.tenant.user.TenantUserView;

public class TenantLdapAuthenticationProvider extends LdapAuthenticationProvider {
    private TenantUserView tenantUserView;

    private SimpleGrantedAuthority tenantUserAuthority = new SimpleGrantedAuthority("ROLE_TENANT_USER");

    public TenantLdapAuthenticationProvider(LdapAuthenticator authenticator, LdapAuthoritiesPopulator authoritiesPopulator, TenantUserView tenantUserView) {
        super(authenticator, authoritiesPopulator);
        this.tenantUserView = tenantUserView;
    }

    @Override
    protected Authentication createSuccessfulAuthentication(UsernamePasswordAuthenticationToken authentication, UserDetails user) {
        String tenantIdentifier = null;
        if (user.getAuthorities().contains(tenantUserAuthority)) {
            SecurityContextHolder.getContext().setAuthentication(null);
            tenantIdentifier = tenantUserView.findByAccount(user.getUsername()).getTenantIdentifier();
        }
        return super.createSuccessfulAuthentication(authentication, new TenantUserDetailsImpl(user, tenantIdentifier));
    }
}
