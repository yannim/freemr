package com.eits.freemr.configuration.upcasting;

import javax.annotation.PostConstruct;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@NoArgsConstructor
@Slf4j
public class ViewUpcaster implements InitializingBean {
    @Autowired
    @Value("${view.upcast}")
    @NonNull
    private Boolean upcastView;

    @Autowired
    @NonNull
    private DataMigrator migrate;

    @Autowired
    @NonNull
    private SchemaMigrator schemaMigrator;

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        if (upcastView) {
            log.info("begin to migrate data for AvailbeAdaptorDefinition");
            schemaMigrator.updateSchema();
            migrate.migrateData();
            log.info("migrate data for AvailbeAdaptorDefinition ok");
        }
    }

}
