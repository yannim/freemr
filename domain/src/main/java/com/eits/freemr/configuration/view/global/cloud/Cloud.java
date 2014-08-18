package com.eits.freemr.configuration.view.global.cloud;

import com.eits.freemr.configuration.common.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.hateoas.Identifiable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity(name = TableConstants.VIEW_PREFIX + "cloud")
@Data
@EqualsAndHashCode(of = "identifier")
@RequiredArgsConstructor
@NoArgsConstructor
public class Cloud implements Identifiable<String>{

    @Id
    @NonNull
    private String identifier;

    @Column(length = 128)
    private String name;

    @Column(length = 1024)
    private String description;

    @Column(length = 36)
    private String tenantIdentifier;

    @Column
    private boolean tenantArchived;

    @Column
    private boolean archived;

    @Override
    public String getId() {
        return identifier;
    }

}
