package com.eits.freemr.configuration.view.global.tenant.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.hateoas.Identifiable;

import com.eits.freemr.configuration.common.TableConstants;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity(name = TableConstants.VIEW_PREFIX + "tenant_user")
@RequiredArgsConstructor
@Data
@EqualsAndHashCode(of = "identifier")
@NoArgsConstructor
public class TenantUser implements Identifiable<String>{
    @Id
    @NonNull
    @Column(length = 36)
    private String identifier;

    @NonNull
    @Column(length = 64)
    private String account;

    @NonNull
    @Column(length = 128)
    private String password;

    @NonNull
    @Column(length = 36)
    private String tenantIdentifier;

    @Column(columnDefinition = "TEXT")
    private String description;
    
    public String getId() {
        return identifier;
    }
}
