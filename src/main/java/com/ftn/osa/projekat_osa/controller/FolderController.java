package com.ftn.osa.projekat_osa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.ftn.osa.projekat_osa.android_dto.MessageDTO;
import com.ftn.osa.projekat_osa.exceptions.ResourceNotFoundException;
import com.ftn.osa.projekat_osa.exceptions.WrongProtocolException;
import com.ftn.osa.projekat_osa.model.Message;
import com.ftn.osa.projekat_osa.utillity.FolderSyncWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.osa.projekat_osa.android_dto.FolderDTO;
import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.service.serviceInterface.FolderServiceInterface;

import javax.mail.MessagingException;

@RestController
@RequestMapping(value = "api/folders")
public class FolderController {

    @Autowired
    private FolderServiceInterface folderService;

    @GetMapping
    public ResponseEntity<List<FolderDTO>> getFolders() {
        List<Folder> folders = folderService.getAll();

        List<FolderDTO> foldersDTO = new ArrayList<FolderDTO>();
        for (Folder f : folders) {
            foldersDTO.add(new FolderDTO(f));
        }
        return new ResponseEntity<List<FolderDTO>>(foldersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FolderDTO> getFolder(@PathVariable("id") Long id) {
        Folder folder = folderService.getOne(id);
        if (folder == null) {
            return new ResponseEntity<FolderDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<FolderDTO>(new FolderDTO(folder), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<FolderDTO> saveFolder(@RequestBody FolderDTO folderDTO) {
        Folder folder = folderDTO.getJpaEntity();
        if(folder.getParentFolder().getId() == null) {
            folder.setParentFolder(null);

        }
        folder = folderService.save(folder);
        return new ResponseEntity<FolderDTO>(new FolderDTO(folder), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<FolderDTO> updateFolder(@RequestBody FolderDTO folderDTO) {
        Folder folder = folderService.getOne(folderDTO.getId());
        if (folder == null) {
            return new ResponseEntity<FolderDTO>(HttpStatus.BAD_REQUEST);
        }

        Folder jpaEntityFromDto = folderDTO.getJpaEntity();
        folder.setName(jpaEntityFromDto.getName());
        folder.setFolders(jpaEntityFromDto.getFolders());
        folder.setMessages(jpaEntityFromDto.getMessages());
        folder.setParentFolder(jpaEntityFromDto.getParentFolder());

        folder = folderService.save(folder);
        return new ResponseEntity<FolderDTO>(new FolderDTO(folder), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteFolder(@PathVariable("id") Long id) {
        Folder folder = folderService.getOne(id);
        if (folder == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }

    @GetMapping("/{id}/innerFolders")
    public ResponseEntity<Set<FolderDTO>> getInnerFolders(@PathVariable("id") Long id){
        Set<Folder> folders = folderService.getInnerFolders(id);
        return new ResponseEntity<>(folders.stream().map(folder -> new FolderDTO(folder)).collect(Collectors.toSet()),
                HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/sync")
    public ResponseEntity<FolderDTO> syncFolder(@PathVariable("id") Long id) throws ResourceNotFoundException, MessagingException, WrongProtocolException {


        Folder folder = folderService.syncFolder(id);

        return new ResponseEntity<>(new FolderDTO(folder), HttpStatus.OK);
    }


}
