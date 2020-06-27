package com.ftn.osa.projekat_osa.service.serviceInterface;

import com.ftn.osa.projekat_osa.exceptions.InvalidConditionException;
import com.ftn.osa.projekat_osa.exceptions.InvalidOperationException;
import com.ftn.osa.projekat_osa.exceptions.ResourceNotFoundException;
import com.ftn.osa.projekat_osa.exceptions.WrongProtocolException;
import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.model.Message;
import com.ftn.osa.projekat_osa.model.Rule;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Set;

public interface AccountServiceInterface {
    public List<Account> getAll();

    public Account getOne(Long accountId);

    public Account save(Account account) throws WrongProtocolException, MessagingException;

    public Account add(Account account) throws WrongProtocolException, MessagingException, InvalidConditionException, InvalidOperationException;

    public void remove(Long accountId);

    public Set<Folder> getAccountFolders(Long accountId);

    public Folder getIndexFolder(Long accountID);

    public Message addMessageToDraftsFolder(Long accountID, Message message) throws ResourceNotFoundException;

    Rule addAccountRule(Long accountId, Rule rule) throws ResourceNotFoundException;

    Set<Rule> getAccountRules(Long accountId) throws ResourceNotFoundException;

    Set<Account> getUserAccounts(Long userId);

    Account addUserAccount(Account account, Long userId) throws MessagingException, InvalidConditionException, InvalidOperationException, WrongProtocolException;
}
