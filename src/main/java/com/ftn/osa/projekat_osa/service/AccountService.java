package com.ftn.osa.projekat_osa.service;

import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.repository.AccountRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.AccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AccountService implements AccountServiceInterface {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Set<Account> getAll() {
        accountRepository.findAll();
    }
}
