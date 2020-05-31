package com.ftn.osa.projekat_osa.service.serviceInterface;

import com.ftn.osa.projekat_osa.exceptions.ResourceNotFoundException;
import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.model.Message;

import javax.mail.MessagingException;
import java.util.Set;

public interface MailServiceInterface {
    public Message sendMessage(Message message) throws ResourceNotFoundException, MessagingException;

    public Set<Folder> getWholeFolderTree(Long accountId) throws ResourceNotFoundException, MessagingException;

    public Folder addFolderToDb(Folder folder, Folder parentFolder);

    Folder syncFolder(Long id) throws ResourceNotFoundException, MessagingException;
}
