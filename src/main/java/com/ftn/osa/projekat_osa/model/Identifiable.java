package com.ftn.osa.projekat_osa.model;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class Identifiable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Integer id;

    public Identifiable() {
        this(null);
    }

    public Identifiable(Integer id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId() == ((Identifiable)obj).getId();
    }
}
