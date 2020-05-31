package com.ftn.osa.projekat_osa.service;

import com.ftn.osa.projekat_osa.exceptions.ResourceNotFoundException;
import com.ftn.osa.projekat_osa.mail_utill.MailUtility;
import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.model.Message;
import com.ftn.osa.projekat_osa.repository.AccountRepository;
import com.ftn.osa.projekat_osa.repository.FolderRepository;
import com.ftn.osa.projekat_osa.repository.MessageRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.MailServiceInterface;
import com.ftn.osa.projekat_osa.utillity.FolderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MailService implements MailServiceInterface {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    FolderRepository folderRepository;

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
            Set<Folder> folderTree = mailUtility.getWholeFolderTree();
            //TODO samo trenutno se ovako dodaje u bazu, potrebno refaktorisati
            folderTree = folderTree.stream().map(folder -> addFolderToDb(folder, null)).collect(Collectors.toSet());
            account.setAccountFolders(folderTree);
            accountRepository.save(account);
            return folderTree;
        }
        else throw new ResourceNotFoundException("Account not found");
    }

    @Override
    public Folder addFolderToDb(Folder folder, Folder parentFolder) {
    //Treba napisati u accountRepository i napisati pomocu transakcija

        Set<Message> folderMessages = folder.getMessages();
        folder.setMessages(new HashSet<>());
        folder = folderRepository.save(folder);
        folder.setParentFolder(parentFolder);
        //finalFolder je potreban posto stream pravi problem, promenjiva mora biti final
        Folder finalFolder = folder;
        //rekurzivno poziva kako bi se svi folderi unutar foldera dodali
        folder.setFolders(folder.getFolders().stream().map(folder1 -> this.addFolderToDb(folder1, finalFolder)).collect(Collectors.toSet()));
        folder.setMessages(new HashSet<>(messageRepository.saveAll(folderMessages)));

        folder = folderRepository.save(folder);

        return folder;
    }

    @Override
    public Folder syncFolder(Long id) throws ResourceNotFoundException, MessagingException {
        Folder folder = folderRepository.getOne(id);
        Stack<String> folderPath = FolderHelper.getFolderPathStack(folder, new Stack<>());
        LocalDateTime latestMessageTimestamp = folder.getMessages().stream()
                .map(message -> message.getDateTime())
                .max((o1, o2) -> o1.isAfter(o2) ? 1 : o1.isBefore(o2) ? -1 : 0)
                .orElse(null);

        List<String> existingFoldersNameList = folder.getFolders().stream()
                .map(folder1 -> folder1.getName())
                .collect(Collectors.toList());

        Folder rootFolder = FolderHelper.getRootFolder(folder);

        Optional<Account> optionalAccount = accountRepository.getAccountFromAccountFolder(rootFolder.getId());
        if(optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            MailUtility mailUtility = new MailUtility(account);
            Map<String, Object> map = mailUtility.syncFolder(folderPath, latestMessageTimestamp, existingFoldersNameList, folder);

            List<Message> messages = (List<Message>) map.get("messages");

            List<Folder> folders = (List<Folder>) map.get("folders");

            messages = messageRepository.saveAll(messages);
            folders = folders.stream().map(folder1 -> addFolderToDb(folder1, folder)).collect(Collectors.toList());

            folder.getMessages().addAll(messages);
            folder.getFolders().addAll(folders);


            return folderRepository.save(folder);

        }
        else throw new ResourceNotFoundException("Account not found");


    }


}
