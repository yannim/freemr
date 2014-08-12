package com.eits.freemr.configuration.rest.api.global.tenantuser;

import lombok.Data;

@Data
public class CreateTenantUserRequest {
    private String tenantIdentifier;

    private String account;

    private String password;

    private String description;

}
