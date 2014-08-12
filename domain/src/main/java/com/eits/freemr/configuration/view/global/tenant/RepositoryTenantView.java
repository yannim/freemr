package com.eits.freemr.configuration.view.global.tenant;

import java.sql.SQLException;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.eits.freemr.configuration.domain.interfaces.events.tenant.TenantArchivedEvent;
import com.eits.freemr.configuration.domain.interfaces.events.tenant.TenantCreatedEvent;
import com.eits.freemr.configuration.domain.interfaces.events.tenant.TenantModifiedEvent;

@Component
@RequiredArgsConstructor
@NoArgsConstructor
public class RepositoryTenantView implements TenantView {

    @Autowired
    @NonNull
    private TenantRepository tenantRepository;

    @Autowired
    @NonNull
    private TenantSchemaExport tenantSchemaExport;


    @Override
    public Page<Tenant> findAll(String exclude, Pageable pageable) {
        if (exclude.equals("")) {
            return tenantRepository.findAll(pageable);
        } else {
            return tenantRepository.findByIdentifierNot(exclude, pageable);
        }
    }

    @EventHandler
    public void on(TenantCreatedEvent event) throws SQLException {
        Tenant tenant = new Tenant(event.getIdentifier());
        tenant.setName(event.getName());
        tenant.setDescription(event.getDescription());
        tenant.setOrganizationName(event.getOrganizationName());
        tenant.setContactInformation(event.getContactInformation());
        tenantRepository.save(tenant);
        tenantSchemaExport.createTenantSchema(event.getIdentifier());
    }

    @EventHandler
    public void on(TenantModifiedEvent event) {
        Tenant tenant = tenantRepository.findOne(event.getIdentifier());
        tenant.setDescription(event.getDescription());
        tenant.setName(event.getName());
        tenant.setOrganizationName(event.getOrganizationName());
        tenant.setContactInformation(event.getContactInformation());
        tenantRepository.save(tenant);
    }

    @EventHandler
    public void on(TenantArchivedEvent event) {
        Tenant tenant = tenantRepository.findOne(event.getIdentifier());
        tenant.setArchived(true);
        tenantRepository.save(tenant);
    }

    @Override
    public Tenant findByIdentifier(String identifier) {
        return tenantRepository.findOne(identifier);
    }

    @Override
    public Page<Tenant> findNonCurrentTenant(String tenantId, Pageable pageable) {
        return tenantRepository.findByIdentifierNot(tenantId, pageable);
    }

    @Override
    public boolean isNameInUseInOtherTenant(String name, String identifier) {
        return tenantRepository.findByNameAndIdentifierNot(name, identifier).size() > 0;
    }

    @Override
    public List<Tenant> findByName(String name) {
        return tenantRepository.findByName(name);
    }

    @Override
    public Page<Tenant> findAllArchived(boolean archived, Pageable pageable) {
        return tenantRepository.findByArchived(archived, pageable);
    }
}
