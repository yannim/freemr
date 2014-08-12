package com.eits.freemr.configuration.domain.interfaces.events.global;

import com.eits.freemr.configuration.domain.interfaces.events.TenantRelatedAggregateOperatedEvent;

import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
public class TenantUserCreatedEvent extends TenantRelatedAggregateOperatedEvent {
    @Getter
    private final String account;

    @Getter
    private final String password;

    @Getter
    private final String description;

    public TenantUserCreatedEvent(String identifier, String tenantIdentifier, String account, String password, String description) {
        super(identifier, tenantIdentifier);
        this.account = account;
        this.password = password;
        this.description = description;
    }

}
