package com.ftn.osa.projekat_osa.service.serviceInterface;

import com.ftn.osa.projekat_osa.exceptions.WrongProtocolException;
import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.model.Folder;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Set;

public interface AccountServiceInterface {
    public List<Account> getAll();

    public Account getOne(Long accountId);

    public Account save(Account account) throws WrongProtocolException, MessagingException;

    public void remove(Long accountId);

    public Set<Folder> getAccountFolders(Long accountId);
}
