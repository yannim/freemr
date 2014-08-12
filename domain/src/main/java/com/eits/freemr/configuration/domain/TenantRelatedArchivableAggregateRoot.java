package com.eits.freemr.configuration.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class TenantRelatedArchivableAggregateRoot<I> extends ArchivableAggregateRoot<I> {
    private static final long serialVersionUID = -5039740887026434576L;

    @Setter(value = AccessLevel.PROTECTED)
    @Getter(value = AccessLevel.PROTECTED)
    private String tenantIdentifier;
}
