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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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
}
