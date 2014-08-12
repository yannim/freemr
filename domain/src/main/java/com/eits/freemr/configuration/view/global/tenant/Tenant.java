package com.eits.freemr.configuration.view.global.tenant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.eits.freemr.configuration.common.TableConstants;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * The Class Tenant.
 */
@Entity(name = TableConstants.TABLE_PREFIX + "tenant")
@RequiredArgsConstructor
@Data
@EqualsAndHashCode(of = "identifier")
@NoArgsConstructor
public class Tenant {

    /** The identifier. */
    @Id
    @NonNull
    @Column(length = 36)
    private String identifier;

    /** The name. */
    @Column(length = 128)
    private String name;

    /** The description. */
    @Column(length = 1024)
    private String description;

    /** The organization name. */
    @Column(length = 255)
    private String organizationName;

    /** The contact information. */
    @Column(length = 255)
    private String contactInformation;

    /** The archive. */
    @Column
    private boolean archived;

}
