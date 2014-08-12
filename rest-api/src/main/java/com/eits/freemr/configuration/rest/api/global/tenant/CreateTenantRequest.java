package com.eits.freemr.configuration.rest.api.global.tenant;

import lombok.Data;

@Data
public class CreateTenantRequest {

    private String name;

    private String description;

    private String organizationName;

    private String contactInformation;

}
