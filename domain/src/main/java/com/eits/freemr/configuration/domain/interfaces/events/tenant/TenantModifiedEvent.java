package com.eits.freemr.configuration.domain.interfaces.events.tenant;

import com.eits.freemr.configuration.domain.interfaces.events.AggregateOperatedEvent;

import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
public class TenantModifiedEvent extends AggregateOperatedEvent {
    @Getter
    private final String name;

    @Getter
    private final String description;

    @Getter
    private final String organizationName;

    @Getter
    private final String contactInformation;

    public TenantModifiedEvent(String identifier, String name, String description, String organizationName, String contactInformation) {
        super(identifier);
        this.description = description;
        this.name = name;
        this.organizationName = organizationName;
        this.contactInformation = contactInformation;
    }
}
