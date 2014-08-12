package com.eits.freemr.configuration.domain.interfaces.events.global;

import com.eits.freemr.configuration.domain.interfaces.events.TenantRelatedAggregateOperatedEvent;

import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
public class CloudCreatedEvent extends TenantRelatedAggregateOperatedEvent {
    @Getter
    private final String name;

    @Getter
    private final String description;

    public CloudCreatedEvent(String identifier, String tenantIdentifier, String name, String description) {
        super(identifier, tenantIdentifier);
        this.name = name;
        this.description = description;
    }

}
