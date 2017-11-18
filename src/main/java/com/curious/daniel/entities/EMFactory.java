package com.curious.daniel.entities;

import org.glassfish.jersey.internal.inject.DisposableSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class EMFactory implements DisposableSupplier<EntityManager> {
    private static final Logger log = LoggerFactory.getLogger(EMFactory.class);

    @Inject
    EntityManagerFactory emf;

    public EMFactory() {
    }

    public void dispose(EntityManager entityManager) {
        log.trace("Disposing EntityManger [{}]", entityManager);
        if (entityManager.isOpen()) {
            entityManager.close();
        }

    }

    public EntityManager get() {
        EntityManager entityManager = this.emf.createEntityManager();
        log.trace("Providing new EntityManger [{}]", entityManager);
        return entityManager;
    }
}