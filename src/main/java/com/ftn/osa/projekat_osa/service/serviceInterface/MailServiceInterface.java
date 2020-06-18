package com.ftn.osa.projekat_osa.service.serviceInterface;

import com.ftn.osa.projekat_osa.exceptions.ResourceNotFoundException;
import com.ftn.osa.projekat_osa.exceptions.WrongProtocolException;
import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.model.Message;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Set;

public interface MailServiceInterface {
    Message sendMessage(Message message) throws ResourceNotFoundException, MessagingException;

    @Deprecated
    Set<Folder> getWholeFolderTree(Long accountId) throws ResourceNotFoundException, MessagingException;

    Folder addFolderToDb(Folder folder, Folder parentFolder);

    @Deprecated
    Folder syncFolder(Long id) throws ResourceNotFoundException, MessagingException;

    Set<Message> getAllMessages(Long accountId) throws WrongProtocolException, MessagingException;

    Set<Message> getNewMessages(Long accountId) throws WrongProtocolException, MessagingException, ResourceNotFoundException;
}
