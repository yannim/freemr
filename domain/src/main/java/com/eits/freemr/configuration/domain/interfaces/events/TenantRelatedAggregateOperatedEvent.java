package com.eits.freemr.configuration.domain.interfaces.events;

import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
public abstract class TenantRelatedAggregateOperatedEvent extends AggregateOperatedEvent {
    @Getter
    private final String tenantIdentifier;

    public TenantRelatedAggregateOperatedEvent(String identifier, String tenantIdentifier) {
        super(identifier);
        this.tenantIdentifier = tenantIdentifier;
    }

}
