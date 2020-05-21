package com.ftn.osa.projekat_osa.service;

import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.repository.AccountRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.AccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AccountService implements AccountServiceInterface {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account getOne(Long accountId) {
        return accountRepository.getOne(accountId);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void remove(Long accountId) {
        accountRepository.deleteById(accountId);
    }

}
