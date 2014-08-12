package com.eits.freemr.configuration.view.global.tenant.user;

import java.util.Collections;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.InetOrgPerson;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import com.eits.freemr.configuration.domain.interfaces.events.global.TenantUserCreatedEvent;


@Component("tenantUserView")
@RequiredArgsConstructor
@NoArgsConstructor
public class RepositoryTenantUserView implements TenantUserView {

    private static String ROLE = "ROLE_TENANT_USER";

    @Autowired
    @NonNull
    private UserDetailsManager userDetailsManager;

    @Autowired
    @NonNull
    private TenantUserRepository tenantUserRepository;

    @EventHandler
    void on(TenantUserCreatedEvent event) {
        InetOrgPerson.Essence essence = new InetOrgPerson.Essence();
        essence.setCn(new String[] { "FREEMR" });
        essence.setSn("FREEMR");
        essence.setUid(event.getAccount());
        essence.setPassword(event.getPassword());
        essence.setAuthorities(Collections.singletonList(new SimpleGrantedAuthority(ROLE)));
        essence.setDn("uid=" + event.getAccount());
        userDetailsManager.createUser(essence.createUserDetails());
        TenantUser tenantUser = new TenantUser(event.getIdentifier(), event.getAccount(), event.getPassword(), event.getTenantIdentifier());
        tenantUser.setDescription(event.getDescription());
        tenantUserRepository.save(tenantUser);

    }

    @Override
    public TenantUser findByAccount(String account) {
        return tenantUserRepository.findByAccount(account);
    }

    @Override
    public Page<TenantUser> listByTenantIdentifier(String tenantIdentifier, Pageable pageable) {
        return tenantUserRepository.findByTenantIdentifier(tenantIdentifier, pageable);
    }

    @Override
    public Page<TenantUser> all(Pageable pageable) {
        return tenantUserRepository.findAll(pageable);
    }

}
