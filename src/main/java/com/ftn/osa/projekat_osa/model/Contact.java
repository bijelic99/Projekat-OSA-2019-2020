package com.ftn.osa.projekat_osa.model;

import java.util.HashSet;
import java.util.Set;

public class Contact extends Identifiable {
    private String firstName;
    private String lastName;
    private String displayName;
    private String email;
    private String note;
    private Set<Photo> contactPhotos;

    public Contact(Long id, String firstName, String lastName, String displayName, String email, String note, Set<Photo> contactPhotos) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.email = email;
        this.note = note;
        this.contactPhotos = contactPhotos;
    }

    public Contact(){
        this.setContactPhotos(new HashSet<>());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Set<Photo> getContactPhotos() {
        return contactPhotos;
    }

    public void setContactPhotos(Set<Photo> contactPhotos) {
        this.contactPhotos = contactPhotos;
    }
}
