package com.eits.freemr.configuration.application.amqp;

import lombok.RequiredArgsConstructor;

import org.axonframework.domain.EventMessage;
import org.axonframework.eventhandling.amqp.RoutingKeyResolver;

@RequiredArgsConstructor
public class MetaDataRoutingKeyResolver implements RoutingKeyResolver {

    private static final String TENANT_IDENTIFIER_KEY = "tenantIdentifier";

    private static final String TENANT_ROUTING_KEY_PREFIX = "tenant.";

    private static final String GLOBAL_ROUTING_KEY = "global";

    @Override
    public String resolveRoutingKey(@SuppressWarnings("rawtypes") EventMessage eventMessage) {
        Object tenantIdentifier = eventMessage.getMetaData().get(TENANT_IDENTIFIER_KEY);
        if (tenantIdentifier == null) {
            return GLOBAL_ROUTING_KEY;
        }
        return TENANT_ROUTING_KEY_PREFIX + tenantIdentifier;
    }
}
