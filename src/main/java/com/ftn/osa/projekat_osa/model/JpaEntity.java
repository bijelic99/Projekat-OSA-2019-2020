package com.ftn.osa.projekat_osa.model;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class JpaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    public JpaEntity(Long id) {
        this.id = id;
    }

    public JpaEntity() {
        this.id = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getClass() == obj.getClass() && this.getId() == ((JpaEntity)obj).getId();
    }
}
