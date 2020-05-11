package com.ftn.osa.projekat_osa.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "folders")
public class Folder extends JpaEntity {
    @Column(nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private Folder parentFolder;
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Message> messages;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parentFolder")
    private Set<Folder> folders;

    public Folder(Long id, String name, Folder parentFolder, Set<Message> messages, Set<Folder> folders) {
        super(id);
        this.name = name;
        this.parentFolder = parentFolder;
        this.messages = messages;
        this.folders = folders;
    }

    public Folder(){
        this.messages = new HashSet<>();
        this.folders = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Folder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(Folder parentFolder) {
        this.parentFolder = parentFolder;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Set<Folder> getFolders() {
        return folders;
    }

    public void setFolders(Set<Folder> folders) {
        this.folders = folders;
    }
}
