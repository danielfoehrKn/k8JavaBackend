package com.curious.daniel.entities;

import org.glassfish.jersey.internal.inject.DisposableSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class EMFFactory implements DisposableSupplier<EntityManagerFactory> {
    public static final String DEFAULT_PERSISTENCE_JNDI = "java:comp/env/jdbc/DefaultDB";
    private static final Logger log = LoggerFactory.getLogger(EMFFactory.class);
    private final String persistenceUnit;
    private final String contextPath;
    private final Map<String, Object> properties;

    public EMFFactory() {
        this("default", DEFAULT_PERSISTENCE_JNDI, new HashMap());
    }

    public EMFFactory(String persistenceUnit, String contextPath, Map<String, Object> properties) {
        this.persistenceUnit = persistenceUnit;
        this.contextPath = contextPath;
        this.properties = properties;
    }

    public EntityManagerFactory get() {
        EntityManagerFactory entityManagerFactory = getEntityManager(this.persistenceUnit, this.contextPath, this.properties);
        log.trace("Providing new EntityManagerFactory [{}]", entityManagerFactory);
        return entityManagerFactory;
    }

    public void dispose(EntityManagerFactory entityManagerFactory) {
        log.trace("Disposing EntityManagerFactory [{}]", entityManagerFactory);
        entityManagerFactory.close();
    }

    public static EntityManagerFactory getEntityManager(String persistenceUnit, String contextPath, Map<String, Object> properties) {
        try {
            log.debug("Lookup of EntityManagerFactory '{}' at '{}'", persistenceUnit, contextPath);
            properties.put("eclipselink.persistencexml", "META-INF/persistence.xml");
            putIfAbsent(properties, "eclipselink.ddl-generation", "create-tables");
            putIfAbsent(properties, "eclipselink.ddl-generationoutput-mode", "database");
            putIfAbsent(properties, "javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
            putIfAbsent(properties, "eclipselink.logging.parameters", "true");
            putIfAbsent(properties, "eclipselink.logging.level", "ALL");
            putIfAbsent(properties, "eclipselink.logging.logger", SLF4JSessionLog.class.getName());

            //Put username and password from environment variables
            putIfAbsent(properties, "javax.persistence.jdbc.url", "jdbc:mysql://google/".concat(System.getenv("CLOUDSQL_DATABASE_NAME")).concat("?cloudSqlInstance=".concat(System.getenv("CLOUDSQL_INSTANCE_CONNECTION_NAME")).concat("&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false")));
            putIfAbsent(properties, "javax.persistence.jdbc.user", System.getenv("DB_USER"));
            putIfAbsent(properties, "javax.persistence.jdbc.password",  System.getenv("DB_PASSWORD"));

            return Persistence.createEntityManagerFactory(persistenceUnit, properties);
        } catch (RuntimeException e) {
            log.error("Could not lookup EntityManagerFactory at '{}'", contextPath);
            return null;
        }
    }

    private static <K, V> V putIfAbsent(Map<K, V> map, K key, V value) {
        V v = map.get(key);
        if (v == null) {
            v = map.put(key, value);
        }
        return v;
    }
}
