package com.eits.freemr.configuration.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import com.eits.freemr.configuration.domain.interfaces.exception.AggregateArchivedException;

public abstract class ArchivableAggregateRoot<I> extends AbstractAnnotatedAggregateRoot<I> {
    private static final long serialVersionUID = 1422210986913678041L;

    @AggregateIdentifier
    @Setter(value = AccessLevel.PROTECTED)
    @Getter(value = AccessLevel.PROTECTED)
    protected I aggregateIdentifier;

    @Setter(value = AccessLevel.PROTECTED)
    @Getter(value = AccessLevel.PROTECTED)
    private boolean archived;

    protected ArchivableAggregateRoot() {
    }

    protected void assertNotArchived() {
        if (archived) {
            throw new AggregateArchivedException(getClass().getSimpleName() + "[" + aggregateIdentifier + "] is archived");
        }
    }

}
