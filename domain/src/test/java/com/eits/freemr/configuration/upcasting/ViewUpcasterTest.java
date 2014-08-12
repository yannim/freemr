package com.eits.freemr.configuration.upcasting;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eits.freemr.configuration.upcasting.DataMigrator;
import com.eits.freemr.configuration.upcasting.SchemaMigrator;
import com.eits.freemr.configuration.upcasting.ViewUpcaster;

public class ViewUpcasterTest {
    private Mockery mockery = new JUnit4Mockery();

    private SchemaMigrator schemaMigrator = mockery.mock(SchemaMigrator.class);

    private ViewUpcaster upcast;

    @BeforeClass
    public static void setUpClass() {
        try {
            URL.setURLStreamHandlerFactory(new MockMavenProtocolFactory());
        } catch (Error e) {

        }
    }

    @Test
    public void testMigrateData() throws Exception {
//        dataMigrate.setAvailableAdapterDefinitionRepository(availableAdapterDefinitionRepository);
        upcast = new ViewUpcaster(true, new DataMigrator() {
            @Override
            public void migrateData() {
                // TODO Auto-generated method stub
            }
        }, schemaMigrator);
        mockery.checking(new Expectations() {
            {
                oneOf(schemaMigrator).updateSchema();
            }
        });

        upcast.afterPropertiesSet();
        mockery.assertIsSatisfied();
    }
}
