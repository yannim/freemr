package com.eits.freemr.configuration.domain.interfaces.events.global;

import com.eits.freemr.configuration.domain.interfaces.events.TenantRelatedAggregateOperatedEvent;

import lombok.ToString;

@ToString(callSuper = true)
public class CloudArchivedEvent extends TenantRelatedAggregateOperatedEvent {

    public CloudArchivedEvent(String identifier, String tenantIdentifier) {
        super(identifier, tenantIdentifier);
    }

}
