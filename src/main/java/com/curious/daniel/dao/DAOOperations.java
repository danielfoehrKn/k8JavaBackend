package com.curious.daniel.dao;


import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by D060239 on 27.10.2017.
 */
public interface DAOOperations<TYPE, KEY> {

    Optional<TYPE> find(KEY key);

    void persist(TYPE entity);

    void delete(TYPE entity);

    Optional<TYPE> modify(KEY id, Consumer<TYPE> consumer);
}
