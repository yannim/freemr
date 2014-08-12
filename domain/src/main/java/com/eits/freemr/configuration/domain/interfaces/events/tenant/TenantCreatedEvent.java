package com.eits.freemr.configuration.domain.interfaces.events.tenant;

import com.eits.freemr.configuration.domain.interfaces.events.AggregateOperatedEvent;

import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
public class TenantCreatedEvent extends AggregateOperatedEvent {

    @Getter
    private final String name;

    @Getter
    private final String description;

    @Getter
    private final String organizationName;

    @Getter
    private final String contactInformation;

    public TenantCreatedEvent(String identifier, String name, String description, String organizationName, String contactInformation) {
        super(identifier);
        this.name = name;
        this.description = description;
        this.organizationName = organizationName;
        this.contactInformation = contactInformation;
    }

}
