package com.eits.freemr.configuration.application.global;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.domain.IdentifierFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eits.freemr.configuration.application.interfaces.services.global.CloudService;
import com.eits.freemr.configuration.domain.interfaces.commands.global.ArchiveCloudCommand;
import com.eits.freemr.configuration.domain.interfaces.commands.global.CreateCloudCommand;
import com.eits.freemr.configuration.domain.interfaces.commands.global.ModifyCloudCommand;

@Service(value = "defaultCloudService")
@RequiredArgsConstructor
@NoArgsConstructor
public class DefaultCloudService implements CloudService {

    @Autowired
    @NonNull
    private CommandGateway commandGateway;

    @Autowired
    @NonNull
    private IdentifierFactory identifierFactory;

    @Override
    public String create(String tenantIdentifier, String name, String description) {
        CreateCloudCommand command = new CreateCloudCommand(identifierFactory.generateIdentifier(), tenantIdentifier, name, description);
        commandGateway.sendAndWait(command);
        return command.getIdentifier();
    }

    @Override
    public void modify(String identifier, String name, String description) {
        commandGateway.sendAndWait(new ModifyCloudCommand(identifier, name, description));
    }

    @Override
    public void archive(String identifier) {
        commandGateway.sendAndWait(new ArchiveCloudCommand(identifier));
    }

}
