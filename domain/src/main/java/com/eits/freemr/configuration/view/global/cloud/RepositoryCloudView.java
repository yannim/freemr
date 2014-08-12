package com.eits.freemr.configuration.view.global.cloud;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.eits.freemr.configuration.domain.interfaces.events.global.CloudArchivedEvent;
import com.eits.freemr.configuration.domain.interfaces.events.global.CloudCreatedEvent;
import com.eits.freemr.configuration.domain.interfaces.events.global.CloudModifiedEvent;
import com.eits.freemr.configuration.domain.interfaces.events.tenant.TenantArchivedEvent;

@Component
@RequiredArgsConstructor
@NoArgsConstructor
public class RepositoryCloudView implements CloudView {

    @Autowired
    @NonNull
    private CloudRepository cloudRepository;

    @EventHandler
    void on(CloudCreatedEvent event) {
        Cloud cloud = new Cloud(event.getIdentifier());
        cloud.setName(event.getName());
        cloud.setDescription(event.getDescription());
        cloud.setTenantIdentifier(event.getTenantIdentifier());
        cloudRepository.save(cloud);
    }

    @EventHandler
    void on(CloudModifiedEvent event) {
        Cloud cloud = cloudRepository.findOne(event.getIdentifier());
        cloud.setName(event.getName());
        cloud.setDescription(event.getDescription());
        cloudRepository.save(cloud);
    }

    @EventHandler
    void on(CloudArchivedEvent event) {
        Cloud cloud = cloudRepository.findOne(event.getIdentifier());
        cloud.setArchived(true);
        cloudRepository.save(cloud);
    }

    @EventHandler
    void on(TenantArchivedEvent event) {
        Page<Cloud> clouds = cloudRepository.findByTenantIdentifier(event.getIdentifier(), null);
        for (Cloud cloud : clouds) {
            cloud.setTenantArchived(true);
        }
        cloudRepository.save(clouds);
    }

    @Override
    public Page<Cloud> listAll(Pageable pageable) {
        return cloudRepository.findAll(pageable);
    }

    @Override
    public Page<Cloud> listByTenantIdentifier(String tenantIdentifier, Pageable pageable) {
        return cloudRepository.findByTenantIdentifier(tenantIdentifier, pageable);
    }

    @Override
    public Cloud findOne(String identifier) {
        return cloudRepository.findOne(identifier);
    }

    @Override
    public Cloud findByName(String name) {
        return cloudRepository.findOneByName(name);
    }

    @Override
    public boolean isNameUsedInOtherCloud(String identifier, String name) {
        return cloudRepository.findByNameAndIdentifierNot(name, identifier).size() > 0;
    }

}
