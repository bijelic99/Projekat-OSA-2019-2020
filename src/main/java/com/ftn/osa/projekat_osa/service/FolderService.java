package com.ftn.osa.projekat_osa.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.repository.FolderRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.FolderServiceInterface;

@Service
public class FolderService implements FolderServiceInterface {

    @Autowired
    FolderRepository folderRepository;

    @Override
    public Folder getOne(Long folderID) {
        return folderRepository.getOne(folderID);
    }

    @Override
    public List<Folder> getAll() {
        return folderRepository.findAll();
    }

    @Override
    public Folder save(Folder folder) {
        return folderRepository.save(folder);
    }

    @Override
    public void remove(Long folderID) {
        folderRepository.deleteById(folderID);
    }

    @Override
    public Set<Folder> getInnerFolders(Long id) {
        return folderRepository.getInnerFolders(id);
    }


}
