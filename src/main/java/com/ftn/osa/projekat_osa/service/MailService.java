package com.ftn.osa.projekat_osa.service;

import com.ftn.osa.projekat_osa.exceptions.ResourceNotFoundException;
import com.ftn.osa.projekat_osa.exceptions.WrongProtocolException;
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

    @Autowired
    RuleService ruleService;

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

    /**
     * Trebalo bi da se poziva iskljucivo kada se doda novi account.
     * Dobavlja sve mailove u index folderu na mail clientu
     * @param accountId id accounta za koji je potrebno dobaviti emailove
     * @return Set sa svim mailovima u index folderu na mail clientu
     * @throws WrongProtocolException
     * @throws MessagingException
     */
    @Override
    public Set<Message> getAllMessages(Long accountId) throws WrongProtocolException, MessagingException {
        Account account = accountRepository.getOne(accountId);
        MailUtility mailUtility = new MailUtility(account);
        Set<Message> messages = mailUtility.getMessages();
        messages = new HashSet<>(messageRepository.saveAll(messages));


        return messages;
    }

    /**
     * Ucitava nove emailove u index folder prosledjenog accounta
     * @param accountId id accounta za koji je potrebno dobaviti emailove
     * @return  nove poruke, takodje cuva poruke u index folder
     * @throws WrongProtocolException
     * @throws MessagingException
     */
    @Override
    public Set<Message> getNewMessages(Long accountId) throws WrongProtocolException, MessagingException, ResourceNotFoundException {
        Account account = accountRepository.getOne(accountId);

        Optional<Folder> optionalFolder = accountRepository.getAccountIndexFolder(accountId);
        if(optionalFolder.isPresent()) {
            Folder indexFolder = optionalFolder.get();

            LocalDateTime latestTimestamp = indexFolder.getMessages().stream()
                    .map(message -> message.getDateTime())
                    .max((o1, o2) -> o1.isAfter(o2) ? 1 : o1.isBefore(o2) ? -1 : 0)
                    .orElse(null);

            MailUtility mailUtility = new MailUtility(account);
            Set<Message> messages = mailUtility.getNewMessages(latestTimestamp);
            messages = new HashSet<>(messageRepository.saveAll(messages));
            indexFolder.getMessages().addAll(messages);

            ruleService.executeRuleSet(accountId, messages);
            Set<Message> messages1 = folderRepository.getOne(indexFolder.getId()).getMessages();
            Set<Message> finalMessages = messages;
            return messages1.stream().filter(message -> finalMessages.contains(message)).collect(Collectors.toSet());
        }
        else throw new ResourceNotFoundException("Can't find index folder");
    }


}
