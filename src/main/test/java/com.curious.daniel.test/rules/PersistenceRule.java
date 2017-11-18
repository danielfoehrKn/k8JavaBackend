package com.curious.daniel.test.rules;

import com.curious.daniel.entities.SLF4JSessionLog;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.InstanceBinding;
import org.junit.rules.ExternalResource;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PersistenceRule extends ExternalResource {
    private EntityManagerFactory emf;
    private String testName;
    private final String persistenceUnit;
    private final Map<String, String> properties = new HashMap();

    public PersistenceRule(String persistenceUnit) {
        this.persistenceUnit = persistenceUnit;
    }

    public PersistenceRule addProperty(String key, String value) {
        this.properties.put(key, value);
        return this;
    }

    public Statement apply(Statement base, Description description) {
        this.testName = description.getDisplayName();
        return super.apply(base, description);
    }

    protected void before() throws Throwable {
        super.before();
        putIfAbsent(this.properties, "eclipselink.ddl-generation", "create-tables");
        putIfAbsent(this.properties, "eclipselink.target-database", "DERBY");
        putIfAbsent(this.properties, "eclipselink.logging.parameters", "true");
        putIfAbsent(this.properties, "eclipselink.logging.logger", SLF4JSessionLog.class.getName());
        putIfAbsent(this.properties, "javax.persistence.jdbc.url", String.format("jdbc:derby:memory:%s;create=true", this.testName));
        this.emf = Persistence.createEntityManagerFactory(this.persistenceUnit, this.properties);
    }

    protected void after() {
        super.after();
        if (this.emf.isOpen()) {
            this.emf.close();
        }

    }

    public EntityManagerFactory getEmf() {
        return this.emf;
    }

    /** @deprecated */
    @Deprecated
    public EntityManager createEm() {
        return this.getEmf().createEntityManager();
    }

    public void transactional(Consumer<EntityManager> consumer) {
        this.withEM((em) -> {
            em.getTransaction().begin();
            consumer.accept(em);
            em.getTransaction().commit();
        });
    }

    public void withEM(Consumer<EntityManager> consumer) {
        EntityManager em = this.getEmf().createEntityManager();
        consumer.accept(em);
        em.close();
    }

    private static <K, V> V putIfAbsent(Map<K, V> map, K key, V value) {
        V v = map.get(key);
        if (v == null) {
            v = map.put(key, value);
        }

        return v;
    }

    public static class Binder extends AbstractBinder {
        private Supplier<PersistenceRule> supplier;

        public Binder(Supplier<PersistenceRule> supplier) {
            this.supplier = supplier;
        }

        protected void configure() {
            PersistenceRule rule = (PersistenceRule)this.supplier.get();
            ((InstanceBinding)this.bind(rule.getEmf()).to(EntityManagerFactory.class)).ranked(1);
        }
    }
}
