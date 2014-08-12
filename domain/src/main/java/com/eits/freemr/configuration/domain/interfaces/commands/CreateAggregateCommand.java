package com.eits.freemr.configuration.domain.interfaces.commands;

import java.io.Serializable;

import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CreateAggregateCommand implements Serializable {
    private static final long serialVersionUID = -4503791798220706756L;

    @Size(max = 36)
    @Getter
    @NonNull
    private final String identifier;

}
