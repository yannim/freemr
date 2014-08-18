package com.eits.freemr.configuration.rest.api.global.tenant;

import java.util.ArrayList;
import java.util.Collection;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.intercept.RunAsUserToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.eits.freemr.configuration.application.interfaces.services.TenantService;
import com.eits.freemr.configuration.infrastructure.multitenancy.SecurityUtils;
import com.eits.freemr.configuration.infrastructure.multitenancy.TenantUserDetails;
import com.eits.freemr.configuration.rest.api.global.validation.TenantValidator;
import com.eits.freemr.configuration.rest.api.infrastructure.AbstractController;
import com.eits.freemr.configuration.view.global.tenant.Tenant;
import com.eits.freemr.configuration.view.global.tenant.TenantView;

@Controller
@RequestMapping(value = "/tenants/")
@RequiredArgsConstructor
@NoArgsConstructor
public class TenantController extends AbstractController {

    @Autowired
    @NonNull
    private TenantValidator tenantValidator;

    @Autowired
    @NonNull
    private TenantService tenantService;

    @Autowired
    @NonNull
    private TenantView tenantView;
    
    private final TenantResourceAssembler tenantResourceAssembler = new TenantResourceAssembler();

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<PagedResources<?>> findAll(@RequestParam(value = "exclude", defaultValue = "", required = false) String exclude, Pageable pageable) {
        Page<Tenant> tenants =  tenantView.findAll(exclude, pageable);
        PagedResources<?> resources = tenantResourceAssembler.toResources(tenants);
        return new ResponseEntity<PagedResources<?>>(resources, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "archived/{archived}/")
    public HttpEntity<PagedResources<?>> findAllArchived(@PathVariable(value = "archived") boolean archived, Pageable pageable) {
        Page<Tenant> tenants = tenantView.findAllArchived(archived, pageable);
        PagedResources<?> resources = tenantResourceAssembler.toResources(tenants);
        return new ResponseEntity<PagedResources<?>>(resources, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<?> create(@RequestBody CreateTenantRequest request) {
        tenantValidator.assertTenantNameNotInUse(request.getName());
        String id = tenantService.create(request.getName(), request.getDescription(), request.getOrganizationName(), request.getContactInformation());
        Tenant entity = tenantView.findByIdentifier(id);
        TenantResource resource = tenantResourceAssembler.toResource(entity);
        return new ResponseEntity<TenantResource>(resource, HttpStatus.OK);   
    }

    @RequestMapping(value = "{identifier}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void modify(@PathVariable String identifier, @RequestBody ModifyTenantRequest request) {
        tenantValidator.assertTenantNameNotInUseInOtherTenant(request.getName(), identifier);
        tenantService.modify(identifier, request.getName(), request.getDescription(), request.getOrganizationName(), request.getContactInformation());
    }

    @RequestMapping(value = "{identifier}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable String identifier) {
        tenantValidator.assertTenantAvailable(identifier);
        tenantService.delete(identifier);
    }

    @RequestMapping(value = "{identifier}", method = RequestMethod.GET)
    public ResponseEntity<TenantResource> findTenant(@PathVariable String identifier) {
        Tenant tenant = tenantView.findByIdentifier(identifier);
        TenantResource resource = tenantResourceAssembler.toResource(tenant);
        return new ResponseEntity<TenantResource>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "run-as-tenant/{identifier}/", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void runAsTenant(@PathVariable(value = "identifier") String identifier) {
        Tenant tenant = tenantView.findByIdentifier(identifier);

        SecurityContext context = SecurityContextHolder.getContext();
        DummyTenantUserDetails userDetails = new DummyTenantUserDetails(identifier);

        userDetails.setUserName(tenant.getName());

        RunAsUserToken runAsUserToken = new RunAsUserToken("key", userDetails, context.getAuthentication().getCredentials(),
                                                           createAuthoritiesCollection("ROLE_TENANT_USER", "ROLE_ADMIN"), context.getAuthentication()
                                                                                                                                 .getClass());
        SecurityContextHolder.getContext().setAuthentication(runAsUserToken);
    }

    @RequestMapping(value = "run-as-admin/", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void runAsAdmin() {
        SecurityContext context = SecurityContextHolder.getContext();

        DummyGlobalUserDetails userDetails = new DummyGlobalUserDetails();
        RunAsUserToken runAsUserToken = new RunAsUserToken("key", userDetails, context.getAuthentication().getCredentials(),
                                                           createAuthoritiesCollection("ROLE_TENANT_USER", "ROLE_ADMIN"), context.getAuthentication()
                                                                                                                                 .getClass());
        SecurityContextHolder.getContext().setAuthentication(runAsUserToken);
    }

    @RequestMapping(value = "query-security/", method = RequestMethod.GET)
    @ResponseBody
    public TenantUserDetailsResponse querySecurity() {
        TenantUserDetails userDetails = SecurityUtils.retrieveTenantUserDetails();
        return new TenantUserDetailsResponse(userDetails.getTenantIdentifier(), userDetails.getUsername(), userDetails.isAccountNonExpired(),
                                             userDetails.isAccountNonLocked(), userDetails.isCredentialsNonExpired(), userDetails.isEnabled(),
                                             userDetails.getAuthorities());
    }

    private Collection<? extends GrantedAuthority> createAuthoritiesCollection(String... roles) {
        Collection<SimpleGrantedAuthority> collection = new ArrayList<SimpleGrantedAuthority>();
        for (String role : roles) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
            collection.add(simpleGrantedAuthority);
        }
        return collection;
    }

}
