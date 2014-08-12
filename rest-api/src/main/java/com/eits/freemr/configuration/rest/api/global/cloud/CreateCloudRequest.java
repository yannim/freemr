package com.eits.freemr.configuration.rest.api.global.cloud;

import lombok.Data;

@Data
public class CreateCloudRequest {

    private String name;

    private String description;

    private String tenantIdentifier;

}
