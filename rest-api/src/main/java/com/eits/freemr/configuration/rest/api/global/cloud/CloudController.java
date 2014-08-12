package com.eits.freemr.configuration.rest.api.global.cloud;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.eits.freemr.configuration.application.interfaces.services.global.CloudService;
import com.eits.freemr.configuration.rest.api.global.validation.CloudValidator;
import com.eits.freemr.configuration.rest.api.global.validation.TenantValidator;
import com.eits.freemr.configuration.rest.api.infrastructure.AbstractController;
import com.eits.freemr.configuration.view.global.cloud.Cloud;
import com.eits.freemr.configuration.view.global.cloud.CloudView;

@Controller
@RequestMapping(value = "/clouds/")
@RequiredArgsConstructor
@NoArgsConstructor
public class CloudController extends AbstractController {

    @Autowired
    @NonNull
    private TenantValidator tenantValidator;

    @Autowired
    @NonNull
    private CloudService cloudService;

    @Autowired
    @NonNull
    private CloudValidator cloudValidator;

    @Autowired
    @NonNull
    private CloudView cloudView;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Page<Cloud> listAll(Pageable pageable) {
        return cloudView.listAll(pageable);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String create(@RequestBody CreateCloudRequest request) {
        String tenantIdentifier = request.getTenantIdentifier();
        tenantValidator.assertTenantAvailable(tenantIdentifier);
        cloudValidator.assertCloudNameNotInUse(request.getName());
        return cloudService.create(tenantIdentifier, request.getName(), request.getDescription());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{identifier}/")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void modify(@PathVariable(value = "identifier") String identifier, @RequestBody ModifyCloudRequest request) {
        cloudValidator.assertCloudAvailable(identifier);
        cloudValidator.assertCloudNameNotUsedByOtherCloud(identifier, request.getName());
        cloudService.modify(identifier, request.getName(), request.getDescription());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{identifier}/")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void archive(@PathVariable(value = "identifier") String identifier) {
        cloudValidator.assertCloudAvailable(identifier);
        cloudService.archive(identifier);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{identifier}/")
    @ResponseBody
    public Cloud findOne(@PathVariable(value = "identifier") String identifier) {
        return cloudView.findOne(identifier);
    }

}
