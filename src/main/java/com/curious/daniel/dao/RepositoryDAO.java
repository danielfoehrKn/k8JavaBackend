package com.curious.daniel.dao;


import com.curious.daniel.entities.News;
import com.curious.daniel.entities.Repositories;

public class RepositoryDAO extends GenericDAO<Repositories, String> {

    public RepositoryDAO() {
        super(Repositories.class);
    }
}
