package com.ftn.osa.projekat_osa.model;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class Identifiable implements Serializable {
    private Long id;

    public Identifiable(Long id) {
        this.id = id;
    }

    public Identifiable() {
        this.id = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
