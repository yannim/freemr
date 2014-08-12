package com.eits.freemr.configuration.domain.interfaces.commands.global;

import com.eits.freemr.configuration.domain.interfaces.commands.OperateAggregateCommand;

import lombok.ToString;

@ToString(callSuper = true)
public class ArchiveCloudCommand extends OperateAggregateCommand {
    private static final long serialVersionUID = 2901689808964443305L;

    public ArchiveCloudCommand(String identifier) {
        super(identifier);
    }

}
