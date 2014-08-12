package com.eits.freemr.configuration.application.interfaces.services.global;

public interface TenantUserService {

    String create(String tenantIdentifier, String account, String password, String description);

}
