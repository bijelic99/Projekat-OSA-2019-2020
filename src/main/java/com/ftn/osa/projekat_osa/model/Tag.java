package com.ftn.osa.projekat_osa.model;

public class Tag extends Identifiable {
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
