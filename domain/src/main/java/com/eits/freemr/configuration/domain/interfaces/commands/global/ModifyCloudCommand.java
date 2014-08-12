package com.eits.freemr.configuration.domain.interfaces.commands.global;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.eits.freemr.configuration.domain.interfaces.commands.OperateAggregateCommand;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString(callSuper = true)
public class ModifyCloudCommand extends OperateAggregateCommand {
    private static final long serialVersionUID = 2440551807225659332L;

    @NonNull
    @Pattern(regexp = "^(?![0-9]+$)(?!.*-$)(?!-)[a-zA-Z0-9-]{1,63}$")
    @Getter
    private final String name;

    @Size(max = 1024)
    @Getter
    private final String description;

    public ModifyCloudCommand(String identifier, String name, String description) {
        super(identifier);
        this.name = name;
        this.description = description;
    }

}
