package com.ftn.osa.projekat_osa.model;

public class Contact extends Identifiable {
    private String firstName;
    private String lastName;
    private String displayName;
    private String email;
    private Photo photo;
    private String format;

    public Contact(Integer id, String firstName, String lastName, String displayName, String email, Photo photo, String format) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.email = email;
        this.photo = photo;
        this.format = format;
    }
    public Contact() {
        this(null, null, null, null, null, null, null);
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

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
