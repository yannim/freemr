package com.eits.freemr.configuration.rest.api.global.tenant;

import lombok.Data;

@Data
public class ModifyTenantRequest {

    private String name;

    private String description;

    private String organizationName;

    private String contactInformation;

}
