package com.eits.freemr.configuration.domain.tenant;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.domain.MetaData;
import org.axonframework.eventhandling.annotation.EventHandler;

import com.eits.freemr.configuration.domain.ArchivableAggregateRoot;
import com.eits.freemr.configuration.domain.interfaces.commands.tenant.ArchiveTenantCommand;
import com.eits.freemr.configuration.domain.interfaces.commands.tenant.CreateTenantCommand;
import com.eits.freemr.configuration.domain.interfaces.commands.tenant.ModifyTenantCommand;
import com.eits.freemr.configuration.domain.interfaces.events.tenant.TenantArchivedEvent;
import com.eits.freemr.configuration.domain.interfaces.events.tenant.TenantCreatedEvent;
import com.eits.freemr.configuration.domain.interfaces.events.tenant.TenantModifiedEvent;

public class Tenant extends ArchivableAggregateRoot<String> {

    private static final long serialVersionUID = 7654497613407202085L;

    private static final String TENANT_IDENTIFIER_KEY = "tenantIdentifier";

    private String name;

    private final Map<String, String> assignedAdapters = new HashMap<String, String>();

    Tenant() {
    }

    private MetaData buildMetaData(String tenantIdentifier) {
        return new MetaData(Collections.singletonMap(TENANT_IDENTIFIER_KEY, tenantIdentifier));
    }

    @CommandHandler
    public Tenant(CreateTenantCommand command, MetaData metaData) {
        apply(new TenantCreatedEvent(command.getIdentifier(), command.getName(), command.getDescription(), command.getOrganizationName(),
                                     command.getContactInformation()), buildMetaData((String) metaData.get(TENANT_IDENTIFIER_KEY)));
    }

    @CommandHandler
    void on(ArchiveTenantCommand command, MetaData metaData) {
        assertNotArchived();
        apply(new TenantArchivedEvent(command.getIdentifier()), buildMetaData((String) metaData.get(TENANT_IDENTIFIER_KEY)));
    }

    @CommandHandler
    void on(ModifyTenantCommand command, MetaData metaData) {
        assertNotArchived();
        apply(new TenantModifiedEvent(command.getIdentifier(), command.getName(), command.getDescription(), command.getOrganizationName(),
                                      command.getContactInformation()), buildMetaData((String) metaData.get(TENANT_IDENTIFIER_KEY)));
    }


    @EventHandler
    void on(TenantCreatedEvent event) {
        setAggregateIdentifier(event.getIdentifier());
        this.name = event.getName();
    }

    @EventHandler
    void on(TenantArchivedEvent event) {
        setArchived(true);
    }


}
