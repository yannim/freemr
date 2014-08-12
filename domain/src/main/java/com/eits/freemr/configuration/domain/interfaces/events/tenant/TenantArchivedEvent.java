package com.eits.freemr.configuration.domain.interfaces.events.tenant;

import com.eits.freemr.configuration.domain.interfaces.events.AggregateOperatedEvent;

import lombok.ToString;

@ToString(callSuper = true)
public class TenantArchivedEvent extends AggregateOperatedEvent {

    public TenantArchivedEvent(String identifier) {
        super(identifier);
    }

}
