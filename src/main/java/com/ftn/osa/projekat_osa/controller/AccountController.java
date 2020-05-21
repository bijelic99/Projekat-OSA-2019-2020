package com.ftn.osa.projekat_osa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftn.osa.projekat_osa.android_dto.AccountDTO;
import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.service.serviceInterface.AccountServiceInterface;

@RestController
@RequestMapping(value = "api/accounts")
public class AccountController {

    @Autowired
    private AccountServiceInterface accountService;

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAccounts() {
        List<Account> accounts = accountService.getAll();

        List<AccountDTO> accountsDTO = new ArrayList<AccountDTO>();
        for (Account account : accounts) {
            accountsDTO.add(new AccountDTO(account));
        }
        return new ResponseEntity<List<AccountDTO>>(accountsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable("id") Long id) {
        Account account = accountService.getOne(id);
        if (account == null) {
            return new ResponseEntity<AccountDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<AccountDTO>(new AccountDTO(account), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AccountDTO> saveAccount(@RequestBody AccountDTO accountDTO) {
        Account account = accountDTO.getJpaEntity();

        account = accountService.save(account);
        return new ResponseEntity<AccountDTO>(new AccountDTO(account), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO accountDTO, @PathVariable("id") Long accountId) {
        Account account = accountService.getOne(accountDTO.getId());

        if (account == null) {
            return new ResponseEntity<AccountDTO>(HttpStatus.BAD_REQUEST);
        }

        Account jpaEntityFromDto = accountDTO.getJpaEntity();

        account.setSmtpAddress(jpaEntityFromDto.getSmtpAddress());
        account.setSmtpPort(jpaEntityFromDto.getSmtpPort());
        account.setInServerType(jpaEntityFromDto.getInServerType());
        account.setInServerAddress(jpaEntityFromDto.getInServerAddress());
        account.setInServerPort(jpaEntityFromDto.getInServerPort());
        account.setUsername(jpaEntityFromDto.getUsername());
        account.setPassword(jpaEntityFromDto.getPassword());
        account.setDisplayName(jpaEntityFromDto.getDisplayName());

        account = accountService.save(account);

        return new ResponseEntity<AccountDTO>(new AccountDTO(account), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") Long accountid) {
        Account account = accountService.getOne(accountid);
        if (account != null) {
            accountService.remove(accountid);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }
}
