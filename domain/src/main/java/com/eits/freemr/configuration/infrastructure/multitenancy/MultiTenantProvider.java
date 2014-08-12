package com.eits.freemr.configuration.infrastructure.multitenancy;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;

public class MultiTenantProvider implements MultiTenantConnectionProvider, ServiceRegistryAwareService {
    private static final long serialVersionUID = -9208519162288221000L;

    private DatasourceConnectionProviderImpl connectionProvider;

    @Override
    public boolean isUnwrappableAs(@SuppressWarnings("rawtypes") Class unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }

    @Override
    public void injectServices(ServiceRegistryImplementor serviceRegistry) {
        @SuppressWarnings("rawtypes")
        Map lSettings = serviceRegistry.getService(ConfigurationService.class).getSettings();
        connectionProvider = new DatasourceConnectionProviderImpl();
        connectionProvider.configure(lSettings);
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return connectionProvider.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        try {
            connection.createStatement().execute("SET SCHEMA '" + SchemaResolver.DEFAULT_SCHEMA + "'");
        } catch (SQLException e) {
            throw new HibernateException("Could not alter JDBC connection to specified schema [" + SchemaResolver.DEFAULT_SCHEMA + "]", e);
        }
        connectionProvider.closeConnection(connection);
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        final Connection connection = getAnyConnection();
        try {
            connection.createStatement().execute("SET SCHEMA '" + tenantIdentifier + "'");
        } catch (SQLException e) {
            throw new HibernateException("Could not alter JDBC connection to specified schema [" + tenantIdentifier + "]", e);
        }
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        releaseAnyConnection(connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

}
