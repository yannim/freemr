package com.eits.freemr.configuration.domain.interfaces.commands.tenant;

import javax.validation.constraints.Size;

import com.eits.freemr.configuration.domain.interfaces.commands.OperateAggregateCommand;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString(callSuper = true)
public class ModifyTenantCommand extends OperateAggregateCommand {

    private static final long serialVersionUID = -1268866952806261259L;

    @NonNull
    @Size(max = 128)
    @Getter
    private final String name;

    @Size(max = 1024)
    @Getter
    private final String description;

    @NonNull
    @Size(max = 255)
    @Getter
    private final String organizationName;

    @NonNull
    @Size(max = 255)
    @Getter
    private final String contactInformation;

    public ModifyTenantCommand(String identifier, String name, String description, String organizationName, String contactInformation) {
        super(identifier);
        this.name = name;
        this.description = description;
        this.organizationName = organizationName;
        this.contactInformation = contactInformation;
    }

}
