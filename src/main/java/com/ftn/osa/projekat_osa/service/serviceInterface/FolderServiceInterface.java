package com.ftn.osa.projekat_osa.service.serviceInterface;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ftn.osa.projekat_osa.exceptions.InvalidConditionException;
import com.ftn.osa.projekat_osa.exceptions.InvalidOperationException;
import com.ftn.osa.projekat_osa.exceptions.ResourceNotFoundException;
import com.ftn.osa.projekat_osa.exceptions.WrongProtocolException;
import com.ftn.osa.projekat_osa.model.Folder;

import javax.mail.MessagingException;

public interface FolderServiceInterface {

    Folder getOne(Long folderID);

    List<Folder> getAll();

    Folder save(Folder folder);

    void remove(Long folderID);

    Set<Folder> getInnerFolders(Long id);

    Folder syncFolder(Long id) throws ResourceNotFoundException, MessagingException, WrongProtocolException, InvalidConditionException, InvalidOperationException;
}
