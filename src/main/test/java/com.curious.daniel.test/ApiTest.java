package com.curious.daniel.test;

import com.curious.daniel.JerseyApplication;
import com.curious.daniel.test.rules.PersistenceRule;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Rule;

import javax.ws.rs.core.Application;
import java.util.logging.Level;


public abstract class ApiTest extends JerseyTest {
    

    @Rule
    public PersistenceRule db = new PersistenceRule("default");
//            .addProperty("javax.persistence.jdbc.url", "jdbc:derby:LocalTestDB;create=true")
//            .addProperty("eclipselink.ddl-generation", "drop-and-create-tables");


    //Add when refactored in Honeycomb
//    @Rule
//    public MockServerRule mockServer = new MockServerRule();

    

    @Override
    protected Application configure() {

        JerseyApplication app = new JerseyApplication();

        //otherwise tests tries to get em from normal EMF instead of the one configured in persistence ruel
        app.register(new PersistenceRule.Binder(() -> db));
        app.register(new LoggingFeature(java.util.logging.Logger.getLogger("org.glassfish.jersey.Jersey"), Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, LoggingFeature.DEFAULT_MAX_ENTITY_SIZE * 10));
//        app.register(new MockServerRule.Binder(() -> mockServer));
        return app;


    }


}