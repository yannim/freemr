package com.eits.freemr.configuration.application.tenant;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.domain.IdentifierFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eits.freemr.configuration.application.interfaces.services.TenantService;
import com.eits.freemr.configuration.domain.interfaces.commands.tenant.ArchiveTenantCommand;
import com.eits.freemr.configuration.domain.interfaces.commands.tenant.CreateTenantCommand;
import com.eits.freemr.configuration.domain.interfaces.commands.tenant.ModifyTenantCommand;

@Service(value = "defaultTenantService")
@RequiredArgsConstructor
@NoArgsConstructor
public class DefaultTenantService implements TenantService {

    @Autowired
    @NonNull
    private CommandGateway commandGateway;

    @Autowired
    @NonNull
    private IdentifierFactory identifierFactory;

    @Override
    public String create(String name, String description, String organizationName, String contactInformation) {
        CreateTenantCommand command = new CreateTenantCommand(identifierFactory.generateIdentifier(), name, description, organizationName, contactInformation);
        commandGateway.sendAndWait(command);
        return command.getIdentifier();
    }

    @Override
    public void modify(String identifier, String name, String description, String organizationName, String contactInformation) {
        commandGateway.sendAndWait(new ModifyTenantCommand(identifier, name, description, organizationName, contactInformation));
    }

    @Override
    public void delete(String identifier) {
        commandGateway.sendAndWait(new ArchiveTenantCommand(identifier));
    }

}
