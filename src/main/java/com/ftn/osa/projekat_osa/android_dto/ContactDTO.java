package com.ftn.osa.projekat_osa.android_dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.ftn.osa.projekat_osa.model.Contact;
import com.ftn.osa.projekat_osa.model.JpaEntity;
import com.ftn.osa.projekat_osa.model.Photo;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;


public class ContactDTO extends DtoObject<Contact> {
    private Long id;
    private String first;
    private String last;
    private String display;
    private String email;
    private String format;
    private PhotoDTO photo;

    public ContactDTO() {

    }

    public ContactDTO(Long id, String first, String last, String display, String email, String format, PhotoDTO photo) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.display = display;
        this.email = email;
        this.format = format;
        this.photo = photo;
    }

    public ContactDTO(Contact entity) {
        this.id = entity.getId();
        this.first = entity.getFirstName();
        this.last = entity.getLastName();
        this.display = entity.getDisplayName();
        this.email = entity.getEmail();
        this.format = entity.getNote();
        if (entity.getContactPhotos() != null && entity.getContactPhotos().size() > 0) {
            Photo p = entity.getContactPhotos().stream().max(Comparator.comparing(JpaEntity::getId)).get();
            this.photo = new PhotoDTO(p);
        } else this.photo = null;

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public PhotoDTO getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoDTO photo) {
        this.photo = photo;
    }

    @Override
    public Contact getJpaEntity() {
        Set<Photo> photoSet = new HashSet<>();
        if (getPhoto() != null) {
            photoSet.add(getPhoto().getJpaEntity());
        }
        return new Contact(getId(), getFirst(), getLast(), getDisplay(), getEmail(), getFormat(), photoSet);
    }
}
