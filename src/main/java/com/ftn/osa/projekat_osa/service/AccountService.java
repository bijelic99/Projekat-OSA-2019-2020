package com.ftn.osa.projekat_osa.service;

import com.ftn.osa.projekat_osa.exceptions.WrongProtocolException;
import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.model.Message;
import com.ftn.osa.projekat_osa.repository.AccountRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.AccountServiceInterface;
import com.ftn.osa.projekat_osa.service.serviceInterface.FolderServiceInterface;
import com.ftn.osa.projekat_osa.service.serviceInterface.MailServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountService implements AccountServiceInterface {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MailServiceInterface mailServiceInterface;

    @Autowired
    FolderServiceInterface folderServiceInterface;

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account getOne(Long accountId) {
        return accountRepository.getOne(accountId);
    }

    @Override
    public Account save(Account account) throws WrongProtocolException, MessagingException {
        return accountRepository.save(account);
    }

    @Override
    public Account add(Account account) throws WrongProtocolException, MessagingException {
        account = accountRepository.save(account);
        Folder indexFolder = new Folder();
        indexFolder.setName("Inbox");
        indexFolder = folderServiceInterface.save(indexFolder);

        Set<Message> messages = mailServiceInterface.getAllMessages(account.getId());
        indexFolder.getMessages().addAll(messages);
        indexFolder = folderServiceInterface.save(indexFolder);

        account.getAccountFolders().add(indexFolder);

        Folder sentFolder = new Folder();
        sentFolder.setName("Sent");
        sentFolder = folderServiceInterface.save(sentFolder);
        account.getAccountFolders().add(sentFolder);


        return accountRepository.save(account);
    }

    @Override
    public void remove(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    @Override
    public Set<Folder> getAccountFolders(Long accountId) {
        return accountRepository.getAccountFolders(accountId);
    }

    @Override
    public Folder getIndexFolder(Long accountID) {
        Optional<Folder> optionalFolder = accountRepository.getAccountIndexFolder(accountID);
        if (optionalFolder.isPresent()) return optionalFolder.get();
        else return null;
    }

}
