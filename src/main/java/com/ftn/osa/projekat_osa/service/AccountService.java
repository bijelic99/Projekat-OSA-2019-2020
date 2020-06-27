package com.ftn.osa.projekat_osa.service;

import com.ftn.osa.projekat_osa.exceptions.InvalidConditionException;
import com.ftn.osa.projekat_osa.exceptions.InvalidOperationException;
import com.ftn.osa.projekat_osa.exceptions.ResourceNotFoundException;
import com.ftn.osa.projekat_osa.exceptions.WrongProtocolException;
import com.ftn.osa.projekat_osa.model.*;
import com.ftn.osa.projekat_osa.repository.AccountRepository;
import com.ftn.osa.projekat_osa.repository.UserRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
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

    @Autowired
    MessageServiceInterface messageServiceInterface;

    @Autowired
    RuleServiceInterface ruleService;

    @Autowired
    UserRepository userRepository;

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
    public Account add(Account account) throws WrongProtocolException, MessagingException, InvalidConditionException, InvalidOperationException {
        account = accountRepository.save(account);
        Folder inboxFolder = new Folder();
        inboxFolder.setName("Inbox");
        inboxFolder = folderServiceInterface.save(inboxFolder);

        //Set<Message> messages = mailServiceInterface.getAllMessages(account.getId());
        //inboxFolder.getMessages().addAll(messages);
        //inboxFolder = folderServiceInterface.save(inboxFolder);


        account.getAccountFolders().add(inboxFolder);

        Folder sentFolder = new Folder();
        sentFolder.setName("Sent");
        sentFolder = folderServiceInterface.save(sentFolder);
        account.getAccountFolders().add(sentFolder);

        Folder draftsFolder = new Folder();
        draftsFolder.setName("Drafts");
        draftsFolder = folderServiceInterface.save(draftsFolder);
        account.getAccountFolders().add(draftsFolder);


        return accountRepository.save(account);
    }

    @Override
    public Account addUserAccount(Account account, Long userId) throws MessagingException, InvalidConditionException, InvalidOperationException, WrongProtocolException {
        account = add(account);
        User user = userRepository.getOne(userId);
        user.getUserAccounts().add(account);
        userRepository.save(user);
        return account;
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

    @Override
    public Message addMessageToDraftsFolder(Long accountID, Message message) throws ResourceNotFoundException {
        Optional<Folder> optionalFolder = accountRepository.getAccountDraftsFolder(accountID);
        if(optionalFolder.isPresent()){
            Folder draftsFolder = optionalFolder.get();
            message = messageServiceInterface.save(message);
            draftsFolder.getMessages().add(message);
            folderServiceInterface.save(draftsFolder);
            return message;
        }
        else throw new ResourceNotFoundException("Drafts folder couldn't be found, are you sure your accountId is correct?");
    }

    @Override
    public Rule addAccountRule(Long accountId, Rule rule) throws ResourceNotFoundException {
        Optional<Account> optional = accountRepository.findById(accountId);
        if(optional.isPresent()){
            Account account = optional.get();
            rule = ruleService.save(rule);
            account.getAccountRules().add(rule);
            accountRepository.save(account);
            return rule;
        }
        else throw new ResourceNotFoundException("Account not found");
    }

    @Override
    public Set<Rule> getAccountRules(Long accountId) throws ResourceNotFoundException {
        Optional<Account> optional = accountRepository.findById(accountId);
        if(optional.isPresent()){
            Account account = optional.get();
            return account.getAccountRules();
        }
        else throw new ResourceNotFoundException("Account not found");
    }

    @Override
    public Set<Account> getUserAccounts(Long userId) {
        return accountRepository.getUserAccounts(userId);
    }
}
