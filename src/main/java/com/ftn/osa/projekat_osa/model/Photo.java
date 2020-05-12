package com.ftn.osa.projekat_osa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "photos")
public class Photo extends JpaEntity {
    @Column(nullable = false)
    private String path;

    public Photo(Long id, String path) {
        super(id);
        this.path = path;
    }

    public Photo() {
        this(null, null);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
