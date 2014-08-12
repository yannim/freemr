package com.eits.freemr.configuration.rest.api.global.validation;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eits.freemr.configuration.domain.interfaces.exception.AggregateArchivedException;
import com.eits.freemr.configuration.rest.api.global.exception.CloudExistsException;
import com.eits.freemr.configuration.rest.api.global.exception.CloudNotFoundException;
import com.eits.freemr.configuration.view.global.cloud.Cloud;
import com.eits.freemr.configuration.view.global.cloud.CloudView;

@Component("cloudValidator")
@RequiredArgsConstructor
@NoArgsConstructor
public class DefaultCloudValidator implements CloudValidator {

    @Autowired
    @NonNull
    private CloudView cloudView;

    public void assertCloudAvailable(String identifier) {
        Cloud cloud = cloudView.findOne(identifier);
        if (cloud == null) {
            throw new CloudNotFoundException("Cloud does not exist");
        }
        if (cloud.isArchived()) {
            throw new AggregateArchivedException("Cloud is already archived");
        }
    }

    public void assertCloudNameNotInUse(String name) {
        Cloud cloud = cloudView.findByName(name);
        if (cloud != null) {
            throw new CloudExistsException("Cloud [" + name + "] does already exist");
        }
    }

    public void assertCloudNameNotUsedByOtherCloud(String identifier, String name) {
        if (cloudView.isNameUsedInOtherCloud(identifier, name)) {
            throw new CloudExistsException("Cloud [" + name + "] does already exist");
        }
    }

}
