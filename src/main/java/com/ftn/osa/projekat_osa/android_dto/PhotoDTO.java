package com.ftn.osa.projekat_osa.android_dto;

import com.ftn.osa.projekat_osa.model.Photo;

public class PhotoDTO extends DtoObject<Photo> {

    private Long id;
    private String path;

    public PhotoDTO(){

    }

    public PhotoDTO(Photo entity) {
        this.id = entity.getId();
        this.path = entity.getPath();
    }

    public PhotoDTO(Long id, String path) {
        this.id = id;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public Photo getJpaEntity() {
        return new Photo(this.getId(), this.getPath());
    }
}
