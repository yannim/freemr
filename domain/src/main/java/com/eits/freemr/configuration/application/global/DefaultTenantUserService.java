package com.eits.freemr.configuration.application.global;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.domain.IdentifierFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eits.freemr.configuration.application.interfaces.services.global.TenantUserService;
import com.eits.freemr.configuration.domain.interfaces.commands.global.CreateTenantUserCommand;

@Service(value = "defaultTenantUserService")
@RequiredArgsConstructor
@NoArgsConstructor
public class DefaultTenantUserService implements TenantUserService {

    @Autowired
    @NonNull
    private CommandGateway commandGateway;

    @Autowired
    @NonNull
    private IdentifierFactory identifierFactory;

    @Override
    public String create(String tenantIdentifier, String account, String password, String description) {
        String identifier = identifierFactory.generateIdentifier();
        commandGateway.sendAndWait(new CreateTenantUserCommand(identifier, tenantIdentifier, account, password, description));
        return identifier;
    }

}
