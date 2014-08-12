package com.eits.freemr.configuration.domain.interfaces.commands;

import java.io.Serializable;

import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class OperateAggregateCommand implements Serializable {
    private static final long serialVersionUID = 2435068486224138412L;

    @Size(max = 36)
    @TargetAggregateIdentifier
    @Getter
    @NonNull
    private final String identifier;
}
