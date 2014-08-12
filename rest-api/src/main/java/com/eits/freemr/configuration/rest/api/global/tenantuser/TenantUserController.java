package com.eits.freemr.configuration.rest.api.global.tenantuser;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eits.freemr.configuration.application.interfaces.services.global.TenantUserService;
import com.eits.freemr.configuration.rest.api.global.validation.TenantUserValidator;
import com.eits.freemr.configuration.rest.api.global.validation.TenantValidator;
import com.eits.freemr.configuration.rest.api.infrastructure.AbstractController;
import com.eits.freemr.configuration.view.global.tenant.user.TenantUser;
import com.eits.freemr.configuration.view.global.tenant.user.TenantUserView;

@Controller
@RequestMapping(value = "/tenant-users/")
@RequiredArgsConstructor
@NoArgsConstructor
public class TenantUserController extends AbstractController {

    @Autowired
    @NonNull
    private TenantValidator tenantValidator;

    @Autowired
    @NonNull
    private TenantUserView tenantUserView;

    @Autowired
    @NonNull
    private TenantUserService tenantUserService;

    @Autowired
    @NonNull
    private TenantUserValidator tenantUserValidator;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String create(@RequestBody CreateTenantUserRequest request) {
        String tenantIdentifier = request.getTenantIdentifier();
        tenantValidator.assertTenantAvailable(tenantIdentifier);
        tenantUserValidator.assertAccountNotExists(request.getAccount());
        return tenantUserService.create(tenantIdentifier, request.getAccount(), request.getPassword(), request.getDescription());
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Page<TenantUser> listAll(Pageable pageable) {
        return tenantUserView.all(pageable);
    }

}
