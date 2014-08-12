package com.eits.freemr.configuration.domain.interfaces.commands.global;

import javax.validation.constraints.Pattern;

import com.eits.freemr.configuration.domain.interfaces.commands.CreateTenantReleatedAggregateCommand;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString(callSuper = true)
public class CreateCloudCommand extends CreateTenantReleatedAggregateCommand {

    private static final long serialVersionUID = 1229991997053401196L;

    @NonNull
    @Pattern(regexp = "^(?![0-9]+$)(?!.*-$)(?!-)[a-zA-Z0-9-]{1,63}$")
    @Getter
    private final String name;

    @Getter
    private final String description;

    public CreateCloudCommand(String identifier, String tenantIdentifier, String name, String description) {
        super(identifier, tenantIdentifier);
        this.name = name;
        this.description = description;
    }

}
