package com.ftn.osa.projekat_osa.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "contacts")
public class Contact extends JpaEntity {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "display_name", nullable = false)
    private String displayName;
    @Column(nullable = false)
    private String email;
    @Column(columnDefinition = "TEXT NOT NULL")
    private String note;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
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
