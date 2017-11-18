package com.curious.daniel.entities;

import org.glassfish.jersey.internal.inject.DisposableSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class EMFFactory implements DisposableSupplier<EntityManagerFactory> {
    public static final String DEFAULT_PERSISTENCE_JNDI = "java:comp/env/jdbc/DefaultDB";
    private static final Logger log = LoggerFactory.getLogger(EMFFactory.class);
    private final String persistenceUnit;
    private final String contextPath;
    private final Map<String, Object> properties;

    public EMFFactory() {
        this("default", "java:comp/env/jdbc/DefaultDB", new HashMap());
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
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup(contextPath);
            properties.put("eclipselink.persistencexml", "META-INF/persistence.xml");
            putIfAbsent(properties, "javax.persistence.nonJtaDataSource", ds);
            putIfAbsent(properties, "eclipselink.ddl-generation", "create-tables");
            putIfAbsent(properties, "eclipselink.ddl-generationoutput-mode", "database");
            putIfAbsent(properties, "eclipselink.target-database", "MySQL");
            putIfAbsent(properties, "javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
            putIfAbsent(properties, "eclipselink.logging.parameters", "true");
            putIfAbsent(properties, "eclipselink.logging.level", "ALL");
            putIfAbsent(properties, "eclipselink.logging.logger", SLF4JSessionLog.class.getName());
            return Persistence.createEntityManagerFactory(persistenceUnit, properties);
        } catch (RuntimeException | NamingException e) {
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