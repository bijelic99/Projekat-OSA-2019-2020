package com.ftn.osa.projekat_osa.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "FOLDERS")
public class Folder extends FolderElement {

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "parentFolder")
    private Set<Folder> folders;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "parentFolder")
    private Set<Message> messages;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rule> folderRules;

    public Folder() {
        this(null, null, null, null, null, null);

    }

    public Folder(Integer id, Folder parentFolder, String name, Set<Folder> folders, Set<Message> messages, Set<Rule> folderRules) {
        super(id, parentFolder);
        this.name = name;
        this.folders = folders;
        this.messages = messages;
        this.folderRules = folderRules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Folder> getFolders() {
        return folders;
    }

    public void setFolders(Set<Folder> folders) {
        this.folders = folders;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Set<Rule> getFolderRules() {
        return folderRules;
    }

    public void setFolderRules(Set<Rule> folderRules) {
        this.folderRules = folderRules;
    }
}
