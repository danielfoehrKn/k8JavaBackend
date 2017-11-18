package com.curious.daniel.dao;

import java.util.Collection;

/**
 * Created by D060239 on 27.10.2017.
 */
public interface DAOCollectionOperations<TYPE, KEY> {

    Collection<TYPE> findAll();

    Collection<TYPE> findAll(int limit, int offset);
}
