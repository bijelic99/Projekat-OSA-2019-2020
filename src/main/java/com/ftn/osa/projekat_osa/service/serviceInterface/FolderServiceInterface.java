package com.ftn.osa.projekat_osa.service.serviceInterface;

import java.util.List;
import java.util.Set;

import com.ftn.osa.projekat_osa.model.Folder;

public interface FolderServiceInterface {

    Folder getOne(Long folderID);

    List<Folder> getAll();

    Folder save(Folder folder);

    void remove(Long folderID);

    Set<Folder> getInnerFolders(Long id);
}
