package com.ftn.osa.projekat_osa.service;

import com.ftn.osa.projekat_osa.exceptions.ResourceNotFoundException;
import com.ftn.osa.projekat_osa.mail_utill.MailUtility;
import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.model.Message;
import com.ftn.osa.projekat_osa.repository.AccountRepository;
import com.ftn.osa.projekat_osa.repository.MessageRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.MailServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Optional;
import java.util.Set;

@Service
public class MailService implements MailServiceInterface {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MessageRepository messageRepository;

    @Override
    public Message sendMessage(Message message) throws ResourceNotFoundException, MessagingException {
        Optional<Account> optionalAccount =  accountRepository.findById(message.getAccount().getId());
        if (optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            MailUtility mailUtility = new MailUtility(account);
            mailUtility.sendMessage(message);


            return messageRepository.save(message);
        }
        else throw new ResourceNotFoundException("Account not found");

    }

    @Override
    public Set<Folder> getWholeFolderTree(Long accountId) throws ResourceNotFoundException, MessagingException {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if(optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            MailUtility mailUtility = new MailUtility(account);
            return mailUtility.getWholeFolderTree();
        }
        else throw new ResourceNotFoundException("Account not found");
    }
}
