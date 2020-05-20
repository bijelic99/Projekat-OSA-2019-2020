package com.ftn.osa.projekat_osa.android_dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftn.osa.projekat_osa.model.Folder;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FolderDTO extends DtoObject<Folder> {
    private Long id;
    private String name;
    private Long parentFolder;
    private Set<Long> folders;
    private Set<MessageDTO> messages;

    public FolderDTO(){
        folders = new HashSet<>();
        messages = new HashSet<>();
    }

    public FolderDTO(Long id, String name, Long parentFolder, Set<Long> folders, Set<MessageDTO> messages) {
        this.id = id;
        this.name = name;
        this.parentFolder = parentFolder;
        this.folders = folders;
        this.messages = messages;
    }

    public FolderDTO(Folder entity) {
        this();
        this.id = entity.getId() != null ? entity.getId() : 0;
        this.name = entity.getName();
        this.parentFolder = entity.getParentFolder() != null ? entity.getParentFolder().getId() : null;
        if(entity.getFolders().size() > 0)
            this.folders = entity.getFolders().stream().map(folder -> folder.getId()).collect(Collectors.toSet());
        if(entity.getMessages().size() > 0)
            this.messages = entity.getMessages().stream().map(message -> new MessageDTO(message)).collect(Collectors.toSet());
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


    public Set<Long> getFolders() {
        return folders;
    }

    public void setFolders(Set<Long> folders) {
        this.folders = folders;
    }

    public Set<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(Set<MessageDTO> messages) {
        this.messages = messages;
    }


    public Long getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(Long parentFolder) {
        this.parentFolder = parentFolder;
    }

    /**
     * <p>
     * Vraca JPA objekat od FolderDTO.
     * Zbog ispravke za infinite loop error, ne vraca dobro parentFolder i listu foldera koju sadrzi.
     * Tj. na njihovom mestu vraca JPA objekte sa samo id popunjenim, ostale vrednosti su null i ukoliko su nam potrebne treba ih preuzeti iz baze.
     * </p>
     * <p>
     * Function returns jpa object representation of FolderDTO.
     * However, due to fix for infinite loop error, it does not return parent folder or folders correctly.
     * It returns new JPA object for each of them with only their id set, rest of the values are null.
     * It is up to the user to find these values in the db and set them.
     * </p>
     * @return new JPA object that needs some editing
     */
    @Override
    public Folder getJpaEntity() {
        return new Folder(this.getId(), this.getName(), new Folder(this.getParentFolder(), null, null, null, null),
                this.getMessages().stream().map(message -> message.getJpaEntity()).collect(Collectors.toSet()),
                this.getFolders().stream().map(folder ->  new Folder(folder, null, null, null, null)).collect(Collectors.toSet()));
    }
}
