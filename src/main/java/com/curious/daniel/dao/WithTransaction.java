package com.curious.daniel.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.function.Consumer;

/**
 * Created by D060239 on 27.10.2017.
 */
public interface WithTransaction {

    default void withTransaction(EntityManager em, Consumer<EntityManager> consumer) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            consumer.accept(em);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive())
                tx.rollback();
            throw e;
        }
    }
}
