package com.ftn.osa.projekat_osa.android_dto;

import com.ftn.osa.projekat_osa.model.Tag;

public class TagDTO extends DtoObject<Tag> {

    private Long id;
    private String name;

    public TagDTO(Tag entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    /**
     *
     */
    public TagDTO() {
        this.id = null;
        this.name = null;
    }

    /**
     * @param id   tag id
     * @param name tag name
     */
    public TagDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name tag name
     */
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public Tag getJpaEntity() {
        return new Tag(getId(), getName());
    }
}
