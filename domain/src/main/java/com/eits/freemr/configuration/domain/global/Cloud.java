package com.eits.freemr.configuration.domain.global;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;

import com.eits.freemr.configuration.domain.TenantRelatedArchivableAggregateRoot;
import com.eits.freemr.configuration.domain.interfaces.commands.global.ArchiveCloudCommand;
import com.eits.freemr.configuration.domain.interfaces.commands.global.CreateCloudCommand;
import com.eits.freemr.configuration.domain.interfaces.commands.global.ModifyCloudCommand;
import com.eits.freemr.configuration.domain.interfaces.events.global.CloudArchivedEvent;
import com.eits.freemr.configuration.domain.interfaces.events.global.CloudCreatedEvent;
import com.eits.freemr.configuration.domain.interfaces.events.global.CloudModifiedEvent;


public class Cloud extends TenantRelatedArchivableAggregateRoot<String> {

    private static final long serialVersionUID = 7846210188888320507L;

    private String name;

    Cloud() {
    }

    @CommandHandler
    public Cloud(CreateCloudCommand command) {
        apply(new CloudCreatedEvent(command.getIdentifier(), command.getTenantIdentifier(), command.getName(), command.getDescription()));
    }

    @CommandHandler
    void on(ModifyCloudCommand command) {
        assertNotArchived();
        apply(new CloudModifiedEvent(command.getIdentifier(), getTenantIdentifier(), command.getName(), command.getDescription()));
    }

    @CommandHandler
    void on(ArchiveCloudCommand command) {
        assertNotArchived();
        apply(new CloudArchivedEvent(command.getIdentifier(), getTenantIdentifier()));
    }

    @EventHandler
    void on(CloudCreatedEvent event) {
        setAggregateIdentifier(event.getIdentifier());
        setTenantIdentifier(event.getTenantIdentifier());
        this.name = event.getName();
    }

    @EventHandler
    void on(CloudModifiedEvent event) {
        this.name = event.getName();
    }

    @EventHandler
    void on(CloudArchivedEvent event) {
        setArchived(true);
    }

}
