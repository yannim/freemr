package com.eits.freemr.configuration.domain.interfaces.events;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public abstract class AggregateOperatedEvent {
    @Getter
    private final String identifier;
}
