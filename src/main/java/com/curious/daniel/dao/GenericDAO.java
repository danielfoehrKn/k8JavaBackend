package com.curious.daniel.dao;

import com.curious.daniel.entities.BaseEntity;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.function.Consumer;

abstract public class GenericDAO<TYPE extends BaseEntity, KEY> implements DAOOperations<TYPE, KEY>, DAOCollectionOperations<TYPE, KEY>, WithTransaction{

    @Inject
    protected EntityManager em;


    protected final Class<TYPE> type;

    public GenericDAO(Class<TYPE> clz) {
        this.type = clz;
    }

    @Override
    public List<TYPE> findAll() {
        TypedQuery<TYPE> query = em.createQuery("SELECT x FROM " + this.type.getSimpleName() + " x order by x.lastModified DESC", type);
        return query.getResultList();
    }

    @Override
    public List<TYPE> findAll(int limit, int offset) {
        TypedQuery<TYPE> query = em.createQuery("SELECT x FROM " + this.type.getSimpleName() + " x order by x.lastModified DESC", type);
        query.setFirstResult(offset);

        if (limit > 0)
            query.setMaxResults(limit);

        return query.getResultList();
    }

    @Override
    public Optional<TYPE> find(KEY key) {
        return Optional.ofNullable(em.find(this.type, key));
    }


    @Override
    public void persist(TYPE entity) {
        entity.setLastModified(new Date());
        runWithTransaction(em -> em.persist(entity));
    }

    @Override
    public void delete(TYPE entity) {
        runWithTransaction(em -> em.remove(entity));
    }

    public void runWithTransaction(Consumer<EntityManager> consumer) {
        withTransaction(em, consumer);
    }

    @Override
    public Optional<TYPE> modify(KEY id, Consumer<TYPE> consumer) {

        Optional<TYPE> optional = find(id);

        return optional.map(entity -> {

            // Map function is only executed if optional is present
            runWithTransaction(em1 -> {
                entity.setLastModified(new Date());
                consumer.accept(entity);
            });
            return entity;
        });
    }
}