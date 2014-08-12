package com.eits.freemr.configuration.view.global.tenant;

import java.sql.SQLException;

/**
 * The Interface TenantSchemaExport.
 */
public interface TenantSchemaExport {

    /**
     * Inits the global tables.
     * 
     * @throws SQLException
     *             the SQL exception
     */
    void initGlobalTables() throws SQLException;

    /**
     * Creates the tenant schema.
     * 
     * @param tenantIdentifier
     *            the tenant identifier
     * @throws SQLException
     *             the SQL exception
     */
    void createTenantSchema(String tenantIdentifier) throws SQLException;

    /**
     * Creates the tables in schema.
     * 
     * @param classes
     *            the classes
     * @param schema
     *            the schema
     */
    void createTablesInSchema(Class<?>[] classes, final String schema);
}
