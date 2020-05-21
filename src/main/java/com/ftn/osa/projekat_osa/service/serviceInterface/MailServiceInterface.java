package com.ftn.osa.projekat_osa.service.serviceInterface;

import com.ftn.osa.projekat_osa.exceptions.ResourceNotFoundException;
import com.ftn.osa.projekat_osa.model.Message;

import javax.mail.MessagingException;

public interface MailServiceInterface {
    public Message sendMessage(Message message) throws ResourceNotFoundException, MessagingException;
}
