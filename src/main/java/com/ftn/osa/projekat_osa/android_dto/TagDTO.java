package com.ftn.osa.projekat_osa.android_dto;


public class TagDTO extends DtoObject<com.ftn.osa.projekat_osa.model.Tag> {
    private Long id;
    private String name;

    public TagDTO(com.ftn.osa.projekat_osa.model.Tag entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    /**
     *
     */
    public TagDTO(){
        this.id = null;
        this.name = null;
    }

    /**
     *
     * @param id tag id
     * @param name tag name
     */
    public TagDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name tag name
     */
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public com.ftn.osa.projekat_osa.model.Tag getJpaEntity() {
        return new com.ftn.osa.projekat_osa.model.Tag(this.getId(), this.getName());
    }
}
