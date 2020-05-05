package com.ftn.osa.projekat_osa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tags")
public class Tag extends Identifiable {
    @Column(nullable = false)
    private String name;

    public Tag(Long id, String name) {
        super(id);
        this.name = name;
    }

    public Tag() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
