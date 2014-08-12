package com.eits.freemr.configuration.domain.interfaces.commands.tenant;

import com.eits.freemr.configuration.domain.interfaces.commands.OperateAggregateCommand;

import lombok.ToString;

@ToString(callSuper = true)
public class ArchiveTenantCommand extends OperateAggregateCommand {

    private static final long serialVersionUID = 1152620773324464863L;

    public ArchiveTenantCommand(String identifier) {
        super(identifier);
    }

}
