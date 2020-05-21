package com.ftn.osa.projekat_osa.service.serviceInterface;

import com.ftn.osa.projekat_osa.model.Account;

import java.util.List;

public interface AccountServiceInterface {
    public List<Account> getAll();

    public Account getOne(Long accountId);

    public Account save(Account account);

    public void remove(Long accountId);
}
