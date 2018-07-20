package com.curious.daniel.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Repositories extends BaseEntity {

    @Id
    private String id;


    public String getId() {
        return id;
    }

    public Repositories setId(String id) {
        this.id = id;
        return this;
    }
}
