package com.eits.freemr.configuration.domain.global;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;

import com.eits.freemr.configuration.domain.TenantRelatedArchivableAggregateRoot;
import com.eits.freemr.configuration.domain.interfaces.commands.global.CreateTenantUserCommand;
import com.eits.freemr.configuration.domain.interfaces.events.global.TenantUserCreatedEvent;

public class TenantUser extends TenantRelatedArchivableAggregateRoot<String> {

    private static final long serialVersionUID = -6935240605027015626L;

    private String account;

    TenantUser() {
    }

    @CommandHandler
    public TenantUser(CreateTenantUserCommand command) {
        apply(new TenantUserCreatedEvent(command.getIdentifier(), command.getTenantIdentifier(), command.getAccount(), command.getPassword(),
                                         command.getDescription()));
    }

    @EventHandler
    void on(TenantUserCreatedEvent event) {
        setAggregateIdentifier(event.getIdentifier());
        setTenantIdentifier(event.getTenantIdentifier());
        account = event.getAccount();
    }

}
