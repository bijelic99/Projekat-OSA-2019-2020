package com.ftn.osa.projekat_osa.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.ftn.osa.projekat_osa.exceptions.InvalidConditionException;
import com.ftn.osa.projekat_osa.exceptions.InvalidOperationException;
import com.ftn.osa.projekat_osa.exceptions.ResourceNotFoundException;
import com.ftn.osa.projekat_osa.exceptions.WrongProtocolException;
import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.model.Message;
import com.ftn.osa.projekat_osa.repository.AccountRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.MailServiceInterface;
import com.ftn.osa.projekat_osa.utillity.FolderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.repository.FolderRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.FolderServiceInterface;

import javax.mail.MessagingException;

@Service
public class FolderService implements FolderServiceInterface {

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private MailServiceInterface mailServiceInterface;

    @Autowired
    private AccountRepository accountRepository;

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
        accountRepository.getAccountForFolder(folderID).ifPresent(account -> {
            account.setAccountFolders(account.getAccountFolders().stream().filter(folder -> folder.getId() != folderID).collect(Collectors.toSet()));
            accountRepository.save(account);
        });
        folderRepository.deleteById(folderID);
    }

    @Override
    public Set<Folder> getInnerFolders(Long id) {
        return folderRepository.getInnerFolders(id);
    }


    @Override
    public Folder syncFolder(Long id) throws ResourceNotFoundException, MessagingException, WrongProtocolException, InvalidConditionException, InvalidOperationException {
        Folder folder = folderRepository.getOne(id);

        Folder rootFolder = FolderHelper.getRootFolder(folder);
        Optional<Account> optionalAccount = accountRepository.getAccountFromAccountFolder(rootFolder.getId());
        if(optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            mailServiceInterface.getNewMessages(account.getId());

            folder = folderRepository.getOne(id);

            return folder;
        }
        else throw new ResourceNotFoundException("Account not found");




    }


}
