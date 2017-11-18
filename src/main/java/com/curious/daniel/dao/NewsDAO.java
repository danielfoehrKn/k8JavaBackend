package com.curious.daniel.dao;


import com.curious.daniel.entities.News;

public class NewsDAO extends GenericDAO<News, Long> {

    public NewsDAO() {
        super(News.class);
    }
}
