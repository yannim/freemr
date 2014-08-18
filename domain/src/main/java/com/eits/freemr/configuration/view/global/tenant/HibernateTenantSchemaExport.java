package com.eits.freemr.configuration.view.global.tenant;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.axonframework.eventstore.jpa.DomainEventEntry;
import org.axonframework.eventstore.jpa.SnapshotEventEntry;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eits.freemr.configuration.view.global.cloud.Cloud;
import com.eits.freemr.configuration.view.global.tenant.user.TenantUser;

@Component
@RequiredArgsConstructor
@NoArgsConstructor
public class HibernateTenantSchemaExport implements TenantSchemaExport {

    @Autowired
    @NonNull
    private DataSource dataSource;

    @Autowired
    @Value("${hibernate.dialect}")
    @NonNull
    private String dialect;

    private static final Class<?>[] GLOBAL_TABLES = { DomainEventEntry.class, SnapshotEventEntry.class, Tenant.class, Cloud.class, TenantUser.class };

    private static final Class<?>[] TENANT_TABLES = { DomainEventEntry.class, SnapshotEventEntry.class };

    @PostConstruct
    public void initGlobalTables() throws SQLException {
        createTablesInSchema(GLOBAL_TABLES, "freemr");
    }

    public synchronized void createTenantSchema(String tenantIdentifier) throws SQLException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.createStatement().execute("CREATE SCHEMA \"" + tenantIdentifier + "\"");
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        createTablesInSchema(TENANT_TABLES, tenantIdentifier);
    }

    public void createTablesInSchema(Class<?>[] classes, final String schema) {
        Configuration configuration = new Configuration();
        for (Class<?> clazz : classes) {
            configuration.addAnnotatedClass(clazz);
        }
        Properties properties = new Properties();
        properties.put(Environment.DATASOURCE, dataSource);
        properties.setProperty(Environment.DIALECT, dialect);
        properties.setProperty(Environment.DEFAULT_SCHEMA, "\"" + schema + "\"");
        configuration.setProperties(properties);

//        SchemaExport se = new SchemaExport(configuration);
//        se.execute(false, true, false, true);
        SchemaUpdate se = new SchemaUpdate(configuration);
        se.execute(false, true);
    }
}
