package com.ftn.osa.projekat_osa.android_dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftn.osa.projekat_osa.model.Folder;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FolderDTO extends DtoObject<Folder> {
    private Long id;
    private String name;
    private FolderDTO parentFolder;
    private Set<FolderDTO> folders;
    private Set<MessageDTO> messages;

    public FolderDTO(){
        folders = new HashSet<>();
        messages = new HashSet<>();
    }

    public FolderDTO(Long id, String name, FolderDTO parentFolder, Set<FolderDTO> folders, Set<MessageDTO> messages) {
        this.id = id;
        this.name = name;
        this.parentFolder = parentFolder;
        this.folders = folders;
        this.messages = messages;
    }

    public FolderDTO(Folder entity) {
        this.id = entity.getId() != null ? entity.getId() : 0;
        this.name = entity.getName();
        this.parentFolder = entity.getParentFolder() != null ? new FolderDTO(entity.getParentFolder()) : null;
        this.folders = entity.getFolders().size() > 0 ?
                entity.getFolders().stream().map(folder -> new FolderDTO(folder)).collect(Collectors.toSet()) : null;
        this.messages = entity.getMessages().size() > 0 ?
                entity.getMessages().stream().map(message -> new MessageDTO(message)).collect(Collectors.toSet()) : null;
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


    public Set<FolderDTO> getFolders() {
        return folders;
    }

    public void setFolders(Set<FolderDTO> folders) {
        this.folders = folders;
    }

    public Set<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(Set<MessageDTO> messages) {
        this.messages = messages;
    }

    @JsonIgnore
    public FolderDTO getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(FolderDTO parentFolder) {
        this.parentFolder = parentFolder;
    }

    @Override
    public Folder getJpaEntity() {
        return new Folder(this.getId(), this.getName(), this.getParentFolder().getJpaEntity(),
                this.getMessages().stream().map(message -> message.getJpaEntity()).collect(Collectors.toSet()),
                this.getFolders().stream().map(folder -> folder.getJpaEntity()).collect(Collectors.toSet()));
    }
}
