package com.eits.freemr.configuration.domain.interfaces.commands.global;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.eits.freemr.configuration.domain.interfaces.commands.CreateTenantReleatedAggregateCommand;

import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
public class CreateTenantUserCommand extends CreateTenantReleatedAggregateCommand {

    private static final long serialVersionUID = 1062727937032406144L;

    @Getter
    @NotNull
    @Size(max = 64)
    private final String account;

    @Getter
    @NotNull
    @Size(max = 128)
    private final String password;

    @Getter
    private final String description;

    public CreateTenantUserCommand(String identifier, String tenantIdentifier, String account, String password, String description) {
        super(identifier, tenantIdentifier);
        this.account = account;
        this.password = password;
        this.description = description;
    }
}
