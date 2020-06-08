package com.ftn.osa.projekat_osa.android_dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.ftn.osa.projekat_osa.model.Folder;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FolderDTO extends DtoObject<Folder> {
    private Long id;
    private String name;
    private FolderMetadataDTO parentFolder;
    private Set<FolderMetadataDTO> folders;
    private Set<MessageDTO> messages;

    public FolderDTO() {
        folders = new HashSet<>();
        messages = new HashSet<>();
    }

    public FolderDTO(Long id, String name, FolderMetadataDTO parentFolder, Set<FolderMetadataDTO> folders, Set<MessageDTO> messages) {
        this.id = id;
        this.name = name;
        this.parentFolder = parentFolder;
        this.folders = folders;
        this.messages = messages;
    }

    public FolderDTO(Folder entity) {
        this();
        this.id = entity.getId();
        this.name = entity.getName();
        Folder parentFolder = entity.getParentFolder();
        this.parentFolder = parentFolder != null ? new FolderMetadataDTO(parentFolder) : null;
        this.folders = entity.getFolders().stream()
                .map(folder -> new FolderMetadataDTO(folder))
                .collect(Collectors.toSet());
        this.messages = entity.getMessages().stream()
                .map(message -> new MessageDTO(message))
                .collect(Collectors.toSet());

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


    public Set<FolderMetadataDTO> getFolders() {
        return folders;
    }

    public void setFolders(Set<FolderMetadataDTO> folders) {
        this.folders = folders;
    }

    public Set<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(Set<MessageDTO> messages) {
        this.messages = messages;
    }


    public FolderMetadataDTO getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(FolderMetadataDTO parentFolder) {
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
     *
     * @return new JPA object that needs some editing
     */
    @Override
    public Folder getJpaEntity() {
        return new Folder(getId(), getName(), getParentFolder() != null ? getParentFolder().getJpaEntity() : null,
                getMessages().stream().map(messageDTO -> messageDTO.getJpaEntity()).collect(Collectors.toSet()),
                getFolders().stream().map(folderMetadataDTO -> folderMetadataDTO.getJpaEntity()).collect(Collectors.toSet())
                );
    }
}
