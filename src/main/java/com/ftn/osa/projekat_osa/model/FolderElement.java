package com.ftn.osa.projekat_osa.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class FolderElement extends Identifiable implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    private Folder parentFolder;

    public  FolderElement(){
        this(null, null);
    }

    public FolderElement(Integer id, Folder parentFolder) {
        super(id);
        this.parentFolder = parentFolder;
    }

    public Folder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(Folder parentFolder) {
        this.parentFolder = parentFolder;
    }
}
