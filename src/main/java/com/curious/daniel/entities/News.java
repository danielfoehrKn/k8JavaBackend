package com.curious.daniel.entities;


import javax.persistence.*;

@Entity
public class News {
    
    @Id
    @GeneratedValue
    private long id;

    @Column(columnDefinition="VARCHAR(5000)",nullable = false)
    private String title;

    @Column(columnDefinition="VARCHAR(5000)")
    private String description;

    private boolean active;

    public boolean isActive() {
        return active;
    }

    public News setActive(boolean active) {
        this.active = active;
        return this;
    }

    public long getId() {
        return id;
    }

    public News setId(long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public News setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public News setDescription(String description) {
        this.description = description;
        return this;
    }
}
