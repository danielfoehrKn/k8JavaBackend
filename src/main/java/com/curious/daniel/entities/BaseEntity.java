package com.curious.daniel.entities;


import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;


@MappedSuperclass
public class BaseEntity {


    public Date lastModified;


    public Date getLastModified() {
        return lastModified;
    }

    public BaseEntity setLastModified(Date lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    @PrePersist
    @PreUpdate
    public void setLastModified() {
        this.lastModified = new Date();
    }
}
