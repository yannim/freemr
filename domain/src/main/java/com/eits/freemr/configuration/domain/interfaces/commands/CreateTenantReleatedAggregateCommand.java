package com.eits.freemr.configuration.domain.interfaces.commands;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString(callSuper = true)
public abstract class CreateTenantReleatedAggregateCommand extends CreateAggregateCommand {
    private static final long serialVersionUID = -4758418309163240154L;

    @Size(max = 36)
    @Getter
    @NonNull
    private final String tenantIdentifier;

    protected CreateTenantReleatedAggregateCommand(String identifier, String tenantIdentifier) {
        super(identifier);
        this.tenantIdentifier = tenantIdentifier;
    }

}
