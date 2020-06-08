package com.ftn.osa.projekat_osa.android_dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.model.Message;

import java.util.HashSet;

public class FolderMetadataDTO {
    private Long id;
    private String name;
    private Integer numberOfFolders;
    private Integer numberOfMessages;

    public FolderMetadataDTO(Long id, String name, Integer numberOfFolders, Integer numberOfMessages) {
        this.id = id;
        this.name = name;
        this.numberOfFolders = numberOfFolders;
        this.numberOfMessages = numberOfMessages;
    }

    public FolderMetadataDTO(Folder folder){
        this.id = folder.getId();
        this.name = folder.getName();
        this.numberOfFolders = folder.getFolders().size();
        this.numberOfMessages = folder.getMessages().size();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfFolders() {
        return numberOfFolders;
    }

    public void setNumberOfFolders(Integer numberOfFolders) {
        this.numberOfFolders = numberOfFolders;
    }

    public Integer getNumberOfMessages() {
        return numberOfMessages;
    }

    public void setNumberOfMessages(Integer numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
    }

    @JsonIgnore
    public Folder getJpaEntity(){
        return new Folder(getId(), getName(), null, new HashSet<>(), new HashSet<>());
    }
}
