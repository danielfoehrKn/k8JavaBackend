package com.curious.daniel.dto;


public class NewsDTO {

    private long id;
    private String title;
    private String description;
    

    public String getDescription() {
        return description;
    }

    public NewsDTO setDescription(String description) {
        this.description= description;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NewsDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public long getId() {
        return id;
    }

    public NewsDTO setId(long id) {
        this.id = id;
        return this;
    }

}
