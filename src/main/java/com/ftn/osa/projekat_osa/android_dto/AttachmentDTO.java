package com.ftn.osa.projekat_osa.android_dto;

import com.ftn.osa.projekat_osa.model.Attachment;

public class AttachmentDTO extends DtoObject<Attachment> {

    private Long id;
    private String data;
    private String type;
    private String name;

    public AttachmentDTO(){

    }

    public AttachmentDTO(Long id, String data, String type, String name) {
        this.id = id;
        this.data = data;
        this.type = type;
        this.name = name;
    }


    public AttachmentDTO(Attachment entity) {
        this.id = entity.getId();
        this.data = entity.getData();
        this.type = entity.getMime_type();
        this.name = entity.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Attachment getJpaEntity() {
        return new Attachment(this.getId(), this.getData(), this.getType(), this.getName());
    }
}
