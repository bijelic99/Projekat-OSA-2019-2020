package com.ftn.osa.projekat_osa.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
    FolderRepository folderRepository;

    @Autowired
    MailServiceInterface mailServiceInterface;

    @Autowired
    AccountRepository accountRepository;

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


    @Override
    public Map<String, Object> syncFolder(Long id, Map<String, Object> data) throws ResourceNotFoundException, MessagingException, WrongProtocolException {

        String strLatestMessageTimestamp = (String) data.get("latestMessageTimestamp");
        LocalDateTime latestMessageTimestamp = strLatestMessageTimestamp != null ? LocalDateTime.parse(strLatestMessageTimestamp, DateTimeFormatter.ISO_DATE_TIME) : null;

        List<Object> objectList = (List<Object>) data.get("folder_list");
        List<Long> folderList = objectList.stream().map(o -> (Long) o).collect(Collectors.toList());

        if(folderList == null) throw new NullPointerException("Folder list cannot be null");

        Folder rootFolder = FolderHelper.getRootFolder(folderRepository.getOne(id));
        Optional<Account> optionalAccount = accountRepository.getAccountFromAccountFolder(rootFolder.getId());
        if(optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            mailServiceInterface.getNewMessages(account.getId());

            Folder f = folderRepository.getOne(id);

            List<Message> messages = f.getMessages().stream()
                    .filter(message -> latestMessageTimestamp == null || message.getDateTime().isAfter(latestMessageTimestamp))
                    .collect(Collectors.toList());

            List<Folder> folders = f.getFolders().stream()
                    .filter(folder -> !folderList.contains(folder.getId()))
                    .collect(Collectors.toList());

            Map<String, Object> map = new HashMap<>();
            map.put("messages", messages);
            map.put("folders", folders);

            return map;
        }
        else throw new ResourceNotFoundException("Account not found");




    }


}
